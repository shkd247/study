package com.example.spring_core1.member;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }
}
