package com.example.spring_core1.discount;


import com.example.spring_core1.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
