package com.example.toby_spring_reactive.week2.lecture8;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureEx {

    // 비동기 작업 끝나고 할 작업을 담고 있는 콜백 interface
    interface SuccessCallback {
        void onSuccess(String result);
    }

    interface ExceptionCallback {
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String> {
        SuccessCallback sc;
        ExceptionCallback ec;

        /**
         * @param callable : 수행할 비동기 작업
         * @param sc : 비동기 작업 성공 후 결과 값 던져 줄 callback
         * @param ec : 비동기 작업 실패시 던져 줄 callback
         */
        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try {
                sc.onSuccess(get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                ec.onError(e.getCause());
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask f = new CallbackFutureTask(()->{
            Thread.sleep(2000);
            if(1==1) throw new RuntimeException("Async Error!");
            log.info("Async");
            return "Hello";
        }
                , s -> System.out.println("Result: " + s)
                , e -> System.out.println("Error: " + e.getMessage()));

        es.execute(f);
        es.shutdown();
    }
}
