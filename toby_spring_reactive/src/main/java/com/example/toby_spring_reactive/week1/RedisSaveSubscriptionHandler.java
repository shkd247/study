package com.example.toby_spring_reactive.week1;

import com.example.toby_spring_reactive.week1.dto.RedisValueDto;
import java.util.concurrent.Flow.Subscription;

public class RedisSaveSubscriptionHandler implements Subscription {

//    private final Subscriber<? super RedisValueDto> _subscriber;
    public RedisValueDto dto;

    @Override
    public void request(long n) {
    }

    @Override
    public void cancel() {

    }
}