package com.koreait.PsnProject.dto;

import lombok.Data; // 또는 @Getter @Setter 직접 추가
import java.time.LocalDateTime;

@Data // Lombok 사용 시 Getter, Setter 자동 생성
public class NoticeDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    // (Notice 테이블에 updatedAt 컬럼도 있다면 추가)
    // private LocalDateTime updatedAt;

    // --- [추가] ---
    // DB의 admin_id 컬럼과 매핑될 필드
    private Long adminId; 

    // (만약 작성자를 member 테이블과 연결하고 싶다면 MemberDTO 관련 필드를 추가할 수도 있습니다)
    // private Long authorId; // 또는 memberId
    // private String authorNickname; // JOIN해서 가져올 경우
}