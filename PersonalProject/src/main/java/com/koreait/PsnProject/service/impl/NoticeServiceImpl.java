package com.koreait.PsnProject.service.impl; // 패키지 확인

import com.koreait.PsnProject.dao.NoticeDAO; // NoticeDAO import
import com.koreait.PsnProject.dto.NoticeDTO;
import com.koreait.PsnProject.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // @Service import

import java.util.List;

@Service // 서비스 Bean으로 등록
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDAO noticeDAO; // NoticeDAO 주입

    @Override
    public List<NoticeDTO> getAllNotices() {
        return noticeDAO.getAllNotices(); // DAO 호출
    }

    @Override
    public NoticeDTO getNoticeById(Long id) {
        return noticeDAO.getNoticeById(id); // DAO 호출
    }

    // [추가] insertNotice 메소드 구현
    @Override
    public void insertNotice(NoticeDTO noticeDTO) {
        // DAO의 insert 메소드를 호출하여 DB에 저장
        noticeDAO.insertNotice(noticeDTO); 
    }

    @Override
    public void updateNotice(NoticeDTO noticeDTO) {
        noticeDAO.updateNotice(noticeDTO); // DAO 호출
    }

    @Override
    public void deleteNotice(Long id) {
        noticeDAO.deleteNotice(id); // DAO 호출
    }
}