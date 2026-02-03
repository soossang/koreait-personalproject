package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.BookmarkDAO;
import com.koreait.PsnProject.dto.BookmarkDTO; // BookmarkDTO import 추가
import com.koreait.PsnProject.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkDAO bookmarkDAO;

    @Override
    public void toggleBookmark(Long hospitalId, Long memberId) {
        // 1. 현재 찜 상태를 확인합니다.
        boolean isAlreadyBookmarked = bookmarkDAO.isBookmarked(memberId, hospitalId);

        if (isAlreadyBookmarked) {
            // 2. 이미 찜(true)한 상태이면, 찜을 해제(삭제)합니다.
            bookmarkDAO.deleteBookmark(memberId, hospitalId);
        } else {
            // 3. 찜하지 않은(false) 상태이면, DTO를 만들어 찜을 추가(등록)합니다.
            BookmarkDTO bookmarkDTO = new BookmarkDTO();
            bookmarkDTO.setUserId(memberId); // DTO 필드명에 맞게 설정
            bookmarkDTO.setHospitalId(hospitalId);
            bookmarkDAO.insertBookmark(bookmarkDTO);
        }
    }

    @Override
    public boolean isBookmarked(Long hospitalId, Long memberId) {
        return bookmarkDAO.isBookmarked(memberId, hospitalId);
    }
}