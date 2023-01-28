package com.example.spring_core1;

import com.example.spring_core1.discount.DiscountPolicy;
import com.example.spring_core1.discount.FixDiscountPolicy;
import com.example.spring_core1.member.MemberRepository;
import com.example.spring_core1.member.MemberService;
import com.example.spring_core1.member.MemberServiceImpl;
import com.example.spring_core1.member.MemoryMemberRepository;
import com.example.spring_core1.order.OrderService;
import com.example.spring_core1.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(getMemberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
    }

    @Bean
    public DiscountPolicy getDiscountPolicy() {
        return new FixDiscountPolicy();
    }

    @Bean
    public MemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }
}
