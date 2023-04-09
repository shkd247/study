package com.example.webflux_assignment.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "MEMBER")
public class MemberEntity {

    @Id
    @Column(value="COMMENT_ID")
    private String memberId;
    @Column(value="PASSWORD")
    private String password;
    @Column(value="NAME")
    private String name;
}
