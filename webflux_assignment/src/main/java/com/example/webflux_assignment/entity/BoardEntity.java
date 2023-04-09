package com.example.webflux_assignment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "BOARD")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardEntity {

    @Id
    @Column(value="BOARD_ID")
    private Long boardId;
    @Column(value="MEMBER_ID")
    private String memberId;
    @Column(value="TITLE")
    private String title;
    @Column(value="CONTENT")
    private String content;
    @Column(value="INS_DATE")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insDate;

}
