package com.example.toby_spring_reactive.week5.lecture9;

import io.netty.channel.nio.NioEventLoopGroup;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@SpringBootApplication
@EnableAsync
@Slf4j
@SuppressWarnings("deprecation")
public class Lecture9Application {

	static final String URL1 = "http://localhost:8081/service?req={req}";
	static final String URL2 = "http://localhost:8081/service2?req={req}";

	@RestController
	public static class MyController {
		@Autowired MyService myService;
//		AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
		AsyncRestTemplate rt = new AsyncRestTemplate();

		// 수행하는 톰캣 스레드 : 1개
		@GetMapping("/rest")
		public DeferredResult<String> rest(int idx) {
			DeferredResult<String> dr = new DeferredResult<>();

			Completion.from(rt.getForEntity(URL1, String.class, "h" + idx))
					.andApply(s-> rt.getForEntity(URL2, String.class, s.getBody())) // return ~
					.andApply(s->myService.work(s.getBody()))
					.andError(e-> dr.setErrorResult(e.toString())) // 비동기 작업 에러 나면 작업 종료
					.andAccept(s -> dr.setResult(s)); // retrun void

			return dr;
		}
	}

	// 비동기 작업 후 콜백 넣는 작업들을 다시 한 번 재정의
	// S = 넘어 온 파라미터
	// T = 결과
	public static class Completion<S, T> {

		Completion next;

		public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
			Completion<S, T> c = new Completion<>();
			lf.addCallback(s->{
				c.complete(s);
			}, e->{
				c.error(e);
			});
			return c;
		}

		void error(Throwable e) {
			// 다음 작업이 있으면 에러 호출
			if(next != null) next.error(e);
		}

		void complete(T s) {
			if(next != null) next.run(s); // 두번째 Completion에 첫번째 비동기 작업의 결과를 넘겨준다
		}

		void run(S value) {
		}

		public void andAccept(Consumer<T> con) {
			Completion<T, Void> c = new AcceptCompletion<>(con);
			this.next = c;
		}

		// 작업 후 결과를 다른 Completion에게 넘겨줌
		public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
			Completion<T, V> c = new ApplyCompletion<>(fn);
			this.next = c;
			return c;
		}

		public Completion<T, T> andError(Consumer<Throwable> econ) {
			Completion<T, T> c = new ErrorCompletion<>(econ);
			this.next = c;
			return c;
		}
	}

	public static class ErrorCompletion<T> extends Completion<T, T> {
		Consumer<Throwable> econ;

		public ErrorCompletion(Consumer<Throwable> econ) {
			this.econ = econ;
		}

		// 이전 단계의 작업 결과물을 넘겨 줌
		@Override
		void run(T value) {
			if(next != null)
				next.run(value);
		}

		@Override
		void error(Throwable e) {
			econ.accept(e);
		}
	}

	public static class AcceptCompletion<S> extends Completion<S, Void> {
		Consumer<S> con;

		public AcceptCompletion(Consumer<S> con) {
			this.con = con;
		}

		// 이전 단계의 작업 결과물을 넘겨 줌
		@Override
		void run(S value) {
			con.accept(value);
		}
	}

	public static class ApplyCompletion<S, T> extends Completion<S, T> {
		Function<S, ListenableFuture<T>> fn;

		public ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
			this.fn = fn;
		}

		@Override
		void run(S value) {
			ListenableFuture<T> lf = fn.apply(value);
			lf.addCallback(s-> complete(s), e-> error(e));
		}
	}

	@Service
	public static class MyService {
		@Async
		public ListenableFuture<String> work(String req) {
			return new AsyncResult<>(req+"/asyncwork");
		}
	}

	@Bean
	public ThreadPoolTaskExecutor myThreadPool() {
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(1);
		te.setMaxPoolSize(1);
		te.initialize();
		return te;
	}

	public static void main(String[] args) {
		SpringApplication.run(Lecture9Application.class, args);
	}

}