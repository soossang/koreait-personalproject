package com.koreait.PsnProject.dto;

import lombok.Data;

@Data
public class HospitalDTO {
    private Long id;
    private Long memberId;
    private String hospitalName;
    private String address;
    private String department;
    private String phone;
    private int doctorCount;
    private String description;
    private float avgRating;

    // ✅ 지도 표시를 위한 위도와 경도 필드가 있는지 확인해주세요.
    // 없다면 이 두 줄을 추가해야 합니다.
    private Double latitude;  // 위도
    private Double longitude; // 경도
}