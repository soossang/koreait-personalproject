package com.koreait.PsnProject.model;

import lombok.Data;

@Data
public class AdminReport {
    private Long id;
    private Long memberId;
    private Long hospitalId;
    private String reportType; // 예: '리뷰 신고', '병원 정보 수정 요청'
    private String reason;
    private String createdAt;
}