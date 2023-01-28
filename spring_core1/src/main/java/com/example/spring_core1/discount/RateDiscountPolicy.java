package com.example.spring_core1.discount;


import com.example.spring_core1.member.Grade;
import com.example.spring_core1.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        return member.getGrade() == Grade.VIP ? (price * discountPercent) / 100 : 0;
    }
}
