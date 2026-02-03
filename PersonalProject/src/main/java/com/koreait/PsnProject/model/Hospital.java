package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Hospital {
    private Long id;
    private String name;
    private String address;
    private String department;
    private String phone;
    private double latitude;
    private double longitude;
    private int likeCount;
    private double ratingAvg;
    private String createdAt;
}