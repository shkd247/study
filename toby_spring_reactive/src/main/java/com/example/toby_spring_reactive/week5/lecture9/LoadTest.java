package com.example.toby_spring_reactive.week5.lecture9;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

// 웹 요청을 동시에 100개 던지는 작업함
@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8080/rest?idx={idx}";

        // java 동기화 기법
        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i = 0; i <100; i++) {
            // execute -> submit 변경
            // submit은 예외 던지게 되어있음. return 값 추가해야 함
            es.submit(()->{
                int idx = counter.addAndGet(1);

                // blocking 시작
                // 스레드 숫자가 parties 숫자와 동일해질때까지 blocking
                barrier.await();
                log.info("Thread {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();

                String res = rt.getForObject(url, String.class, idx);

                sw.stop();
                log.info("Elapsed: {} {} / {} ", idx, sw.getTotalTimeSeconds(), res);
                return null;
            });
        }

        barrier.await();
        StopWatch main = new StopWatch();
        main.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        log.info("total: {}", main.getTotalTimeSeconds());
    }
}
