package com.example.spring_core1.member;

import com.example.spring_core1.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
//        memberService = appConfig.memberService();
    }

    @Test
    @DisplayName("회원가입")
    void 회원가입() throws Exception {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
