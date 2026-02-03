package com.koreait.PsnProject.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminReportDTO {
    private Long id;
    private Long reportedPostId;   // 신고된 게시글 ID
    private Long reporterUserId;   // 신고자
    private String reason;         // 신고 이유
    private String status;         // 처리 상태 ("대기", "처리완료")
    private LocalDateTime createdAt;
}