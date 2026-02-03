package com.koreait.PsnProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.koreait.PsnProject.dto.BookmarkDTO;

@Mapper
public interface BookmarkDAO {

    // 특정 병원을 찜한 사용자 수
    int countBookmarksByHospital(@Param("hospitalId") Long hospitalId);

    // 사용자가 해당 병원을 찜했는지 여부
    boolean isBookmarked(@Param("userId") Long userId, @Param("hospitalId") Long hospitalId);

    // 병원 찜 추가
    int insertBookmark(BookmarkDTO bookmark);

    // 병원 찜 해제
    int deleteBookmark(@Param("userId") Long userId, @Param("hospitalId") Long hospitalId);

    // 사용자의 찜 목록
    List<BookmarkDTO> getBookmarksByUser(@Param("userId") Long userId);
}