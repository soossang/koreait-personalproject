package com.koreait.PsnProject.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String nickname;
    private String role;
    private boolean active;
}