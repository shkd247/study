package com.example.toby_spring_reactive.week6.lecture11;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CFuture {

    public static void main(String[] args) throws InterruptedException {
        // 새로운 비동기 작업 요청할 때마다 계속 다음 스레드 할당하다가 10개를 모두 사용하면 캐시로 돌아온 스레드를 다시 사용
        ExecutorService es = Executors.newFixedThreadPool(10);
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("runAsync");
                    return 1;
                })
                .thenCompose(s -> {
                    log.info("thenCompose {}",s);
                    return CompletableFuture.completedFuture(s + 1);
                })
                .thenApplyAsync(s2 -> {
                    log.info("thenApplyAsync {}",s2);
                    return s2 * 3;
                }, es)
                .exceptionally(e -> -10)
                .thenAcceptAsync(s3 -> log.info("thenAcceptAsync {}",s3), es);
        log.info("exit");
        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
