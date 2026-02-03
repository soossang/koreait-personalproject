package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Banner {
    private Long id;
    private String imageUrl;
    private String linkUrl;
    private String description;
    private String createdAt;
}