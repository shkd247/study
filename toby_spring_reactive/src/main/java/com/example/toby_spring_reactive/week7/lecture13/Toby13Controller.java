package com.example.toby_spring_reactive.week7.lecture13;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class Toby13Controller {

//    @GetMapping("/")
//    Mono<String> example1() {
//        return Mono.just("Hello WebFlux").log(); // Publisher -> (Publisher) -> (Publisher) -> Subscriber
//    }

//    @GetMapping("/")
//    Mono<String> example2() {
//        // 이 controller 메서드가 끝나고 나서 mono log가 찍힘
//        log.info("pos1");
//        // mono, flux 같은 publisher들은 subscriber가 subscribe해야 동작한다
//        // return 후 spring에서 subscribe하면 호출 됨
//        Mono m = Mono.just("Hello WebFlux").doOnNext(c->log.info(c)).log(); // Publisher -> (Publisher) -> (Publisher) -> Subscriber
//        log.info("pos2");
//        return m;
//    }

//    @GetMapping("/")
//    Mono<String> example3() {
//        // 이 controller 메서드가 끝나고 나서 mono log가 찍힘
//        log.info("pos1");
//        // mono, flux 같은 publisher들은 subscriber가 subscribe해야 동작한다
//        // return 후 spring에서 subscribe하면 호출 됨
//        // just에 service 로직 넣으면 동기적으로 실행되기 때문에 먼저 실행 되고 결과 값이 just에 들어간다. 이후 과정은 메서드 끝나고 동작
//        // just = 미리 준비된 것
//        Mono m = Mono.just(generateHello()).doOnNext(c->log.info(c)).log(); // Publisher -> (Publisher) -> (Publisher) -> Subscriber
//        log.info("pos2");
//        return m;
//    }

//    @GetMapping("/")
//    Mono<String> example4() {
//        log.info("pos1");
//        // parameter: X, return: O
//        Mono m = Mono.fromSupplier(() -> generateHello()).doOnNext(c->log.info(c)).log(); // Publisher -> (Publisher) -> (Publisher) -> Subscriber
//        log.info("pos2");
//        return m;
//    }

//    @GetMapping("/")
//    Mono<String> example5() {
//        log.info("pos1");
//        // parameter: X, return: O
//        Mono m = Mono.fromSupplier(() -> generateHello()).doOnNext(c->log.info(c)).log(); // Publisher -> (Publisher) -> (Publisher) -> Subscriber
//        m.subscribe();
//        log.info("pos2");
//        return m;
//    }

    @GetMapping("/")
    Mono<String> example6() {
        log.info("pos1");
        String msg = generateHello();
        Mono<String> m = Mono.just(msg).doOnNext(c->log.info(c)).log(); // Publisher -> (Publisher) -> (Publisher) -> Subscriber
        // Mono -> String
        // 지금 버전에서는 block 안됨
        // 내부에서 subscribe 동작함
        String msg2 = m.block();
        log.info("pos2: " + msg2);
        return m;
    }

    private String generateHello() {
        log.info("method generateHello()");
        return "Hello Mono";
    }
}
