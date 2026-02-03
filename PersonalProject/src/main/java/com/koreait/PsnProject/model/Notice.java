package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Notice {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
}