package com.example.spring_core1.order;

import com.example.spring_core1.AppConfig;
import com.example.spring_core1.member.Grade;
import com.example.spring_core1.member.Member;
import com.example.spring_core1.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
//        memberService = appConfig.memberService();
//        orderService = appConfig.orderService();
    }

    @Test
    @DisplayName("주문_생성")
    void 주문_생성() throws Exception {
        //given
        Long id = 1L;
        Member memberA = new Member(id, "memberA", Grade.VIP);
        memberService.join(memberA);
        //when
        Order order = orderService.createOrder(id, "itemA", 10000);
        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
