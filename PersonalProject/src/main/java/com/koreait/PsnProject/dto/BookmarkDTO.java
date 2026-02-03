package com.koreait.PsnProject.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookmarkDTO {
    private Long id;
    private Long hospitalId;
    private Long userId;
    private LocalDateTime createdAt;
}