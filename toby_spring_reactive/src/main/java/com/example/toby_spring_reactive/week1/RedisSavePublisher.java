package com.example.toby_spring_reactive.week1;

import com.example.toby_spring_reactive.week1.dto.RedisValueDto;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class RedisSavePublisher implements Publisher<RedisValueDto> {
    private RedisSaveSubscriptionHandler redisSaveSubscriptionHandler;

    @Override
    public void subscribe(Subscriber<? super RedisValueDto> subscriber) {
        subscriber.onSubscribe(redisSaveSubscriptionHandler);
    }

    public void publish(RedisValueDto redisValueDto, int i) {
        redisSaveSubscriptionHandler.request(i);
    }
}