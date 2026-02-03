package com.koreait.PsnProject.dto;

import lombok.Data;

@Data
public class BannerDTO {
    private Long id;
    private Long hospitalId;
    private String imageUrl;
    private String linkUrl;
    private String slot;  // ex) BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    private boolean active;
}