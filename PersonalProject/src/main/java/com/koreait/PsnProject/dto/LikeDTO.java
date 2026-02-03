package com.koreait.PsnProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor // 👈 1. 기본 생성자 추가
@AllArgsConstructor // 👈 2. 모든 필드를 받는 생성자 추가
public class LikeDTO {
    private Long id;
    private Long hospitalId;
    private Long userId;
    private LocalDateTime createdAt;
}