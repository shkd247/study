package com.example.spring_core1.discount;


import com.example.spring_core1.member.Grade;
import com.example.spring_core1.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        return member.getGrade() == Grade.VIP ? discountFixAmount : 0;
    }
}
