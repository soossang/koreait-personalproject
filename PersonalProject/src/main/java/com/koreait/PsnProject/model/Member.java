package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String role; // USER, ADMIN, HOSPITAL 등
    private String profileImg;
    private String createdAt;
}