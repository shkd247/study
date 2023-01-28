package com.example.toby_spring_reactive.week1.dto;

public class RedisValueDto {

    public String key;
    public String value;

    public RedisValueDto(String key, String value){
        this.key = key;
        this.value = value;
    }

}