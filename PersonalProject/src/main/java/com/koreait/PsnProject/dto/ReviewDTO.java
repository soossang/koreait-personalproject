package com.koreait.PsnProject.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private Long hospitalId;
    private Long userId;
    private String userNickname;
    private int rating;         // 평점 (1~5)
    private String content;
    private LocalDateTime createdAt;
}