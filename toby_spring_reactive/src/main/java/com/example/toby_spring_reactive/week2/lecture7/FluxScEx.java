package com.example.toby_spring_reactive.week2.lecture7;


import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxScEx {

    public static void main(String[] args) {
        // reactor : reactive streams 적용한 라이브러리

        // pub
        Flux.range(1, 10)
                .publishOn(Schedulers.newSingle("pub"))
                .log()
                .subscribeOn(Schedulers.newSingle("sub"))
                .subscribe(System.out::println);

        System.out.println("exit");
    }
}
