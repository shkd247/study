package com.example.webflux_assignment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "COMMENTS")
public class CommentEntity {

    @Id
    @Column(value="COMMENT_ID")
    private Long commentId;
    @Column(value="BOARD_ID")
    private Long boardId;
    @Column(value="MEMBER_ID")
    private String memberId;
    @Column(value="CONTENT")
    private String content;
    @Column(value="INS_DATE")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insDate;
}
