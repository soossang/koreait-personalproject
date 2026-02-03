package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Bookmark {
    private Long id;
    private Long memberId;
    private Long hospitalId;
    private String createdAt;
}