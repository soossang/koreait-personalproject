package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Board {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private int viewCount;
    private String createdAt;
}
