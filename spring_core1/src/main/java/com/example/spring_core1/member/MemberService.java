package com.example.spring_core1.member;

public interface MemberService {

    void join(Member member);
    Member findMember(Long id);
}
