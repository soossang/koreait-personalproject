package com.koreait.PsnProject.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MemberDTO {

    private int memberId;
    private String role; // DB 컬럼과 일치하는 필드 이름
    private String username;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private String phone;
    private String profileImage;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalIntro;
    private String medicalDept;
    private int doctorCount;
    private int treatmentCost;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String adminCode;
}