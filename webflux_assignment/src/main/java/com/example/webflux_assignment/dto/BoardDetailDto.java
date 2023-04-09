package com.example.webflux_assignment.dto;

import lombok.Data;

@Data
public class BoardDetailDto {

    private String memberId;
    private String title;
    private String content;
}
