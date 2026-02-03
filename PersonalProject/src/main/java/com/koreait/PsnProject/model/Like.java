package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Like {
    private Long id;
    private Long memberId;
    private Long hospitalId;
    private String createdAt;
}