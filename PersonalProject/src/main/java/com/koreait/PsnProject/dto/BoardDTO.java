package com.koreait.PsnProject.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ 작성자 닉네임을 담을 필드 추가
    private String authorNickname;
}