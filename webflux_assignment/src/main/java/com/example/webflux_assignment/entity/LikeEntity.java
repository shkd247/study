package com.example.webflux_assignment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "LIKES")
public class LikeEntity {

    @Id
    @Column(value="LIKE_ID")
    private Long likeId;
    @Column(value="BOARD_ID")
    private Long boardId;
    @Column(value="MEMBER_ID")
    private String memberId;
    @Column(value="LIKE_YN")
    private boolean likeYn;
    @Column(value="INS_DATE")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insDate;
}
