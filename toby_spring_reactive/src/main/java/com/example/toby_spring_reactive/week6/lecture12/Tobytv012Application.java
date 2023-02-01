package com.example.toby_spring_reactive.week6.lecture12;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@EnableAsync
@Slf4j
public class Tobytv012Application {

    static final String URL1 = "http://localhost:8081/service?req={req}";;
    static final String URL2 = "http://localhost:8081/service2?req={req}";;
    @Autowired
    MyService myService;
    WebClient client = WebClient.create();
    @GetMapping("/rest")
    public Mono<String> rest(int idx) {
        // netty의 워커 스레드에서 동작
        return client.get().uri(URL1, idx).exchange() // Mono<ClientResponse>
                .flatMap(c ->  c.bodyToMono(String.class)) // Mono<String>
                //.doOnNext(c -> log.info(c))
                .flatMap(res1 -> client.get().uri(URL2, res1).exchange()) // Mono<ClientResponse>
                .flatMap(c -> c.bodyToMono((String.class))) // Mono<String>
                .doOnNext(c -> log.info(c))
                .flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)))      // CompletableFuture<String> -> Mono<String> // CompletableFuture는 CompletionStage interface를 상속함
                .doOnNext(c -> log.info(c));
    }
    public static void main(String[] args) {
        System.setProperty("reactor.netty.ioWorkerCount", "1");
        System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
        SpringApplication.run(Tobytv012Application.class, args);
    }
    @Service
    public static class MyService {
        // 비동기 호출
        @Async
        public CompletableFuture<String> work(String req) {
            return CompletableFuture.completedFuture(req + "/asyncwork");
        }
    }
}
