package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private Long hospitalId;
    private Long memberId;
    private String nickname;
    private String content;
    private int rating;
    private String createdAt;
}