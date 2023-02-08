package com.example.toby_spring_reactive.week7.lecture14;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class Toby14Controller {

//    // RestController -> object json 형태로 리턴
//    @GetMapping("/event/{id}")
//    Mono<Event> example0(@PathVariable long id) {
//        return Mono.just(new Event(id, "event" + id));
//    }
//
//    //Flux : 데이터가 여러 개일 때 사용
//    @GetMapping("/events")
//    Flux<Event> example1() {
//        return Flux.just(new Event(1L, "event1"), new Event(2L, "event2"));
//    }

//    @GetMapping("/event/{id}")
//    Mono<List<Event>> example2(@PathVariable long id) {
//        List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
//        return Mono.just(list);
//    }

//    // Flux 요소를 나눠서 보내고 싶다면 MediaType 세팅 ( content-type : text/event-stream )
//    @GetMapping(value="/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    Flux<Event> example3() {
//        List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
//        return Flux.fromIterable(list);
//    }
//
//    @GetMapping(value="/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    Flux<Event> example4() {
//        return Flux
//                .fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), "value"))) // supplier : parameter X, return O
//                .delayElements(Duration.ofSeconds(1))
//                .take(10); // 이벤트 데이터 10개로 제한, 10개 이후엔 cancel() 호출
//    }

    // flux 기능을 이용해 데이터 생성
//    @GetMapping(value="/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    Flux<Event> example5() {
//        // sink : 데이터를 계속 보내는 역할
//        return Flux
//                .<Event>generate(sink -> sink.next(new Event(System.currentTimeMillis(), "value")))
//                .delayElements(Duration.ofSeconds(1))
//                .take(10);
//    }

//    @GetMapping(value="/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    Flux<Event> example6() {
//        return Flux
//                .<Event, Long>generate(()->1L, (id, sink) -> {
//                    sink.next(new Event(id, "value" + id));
//                    return id+1;
//                })
//                .delayElements(Duration.ofSeconds(1))
//                .take(10);
//    }

//    @GetMapping(value="/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    Flux<Event> example7() {
//        Flux<Event> es = Flux
//                .<Event, Long>generate(()->1L, (id, sink) -> {
//                    sink.next(new Event(id, "value" + id));
//                    return id+1;
//                });
//        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
//        // 2개 이상의 flux를 묶어 줌
//        // es, interval를 묶어서 tuple로 만듦
//        return Flux.zip(es, interval).map(tu->tu.getT1()).take(10);
//    }

    @GetMapping(value="/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> example8() {
        Flux<String> es = Flux.generate(sink -> sink.next("Value"));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(es, interval).map(tu -> new Event(tu.getT2()+1, tu.getT1())).take(10);
    }
}
