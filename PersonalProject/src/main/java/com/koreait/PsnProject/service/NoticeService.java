package com.koreait.PsnProject.service; // 패키지 확인

import com.koreait.PsnProject.dto.NoticeDTO;
import java.util.List;

public interface NoticeService {

    /** 공지사항 전체 목록 조회 */
    List<NoticeDTO> getAllNotices();

    /** ID로 공지사항 상세 조회 */
    NoticeDTO getNoticeById(Long id);

    /** [추가] 공지사항 등록 */
    void insertNotice(NoticeDTO noticeDTO); // 👈 이 메소드 선언을 추가하세요.

    /** 공지사항 수정 */
    void updateNotice(NoticeDTO noticeDTO);

    /** 공지사항 삭제 */
    void deleteNotice(Long id);

    // (필요 시 다른 메소드 추가)

}