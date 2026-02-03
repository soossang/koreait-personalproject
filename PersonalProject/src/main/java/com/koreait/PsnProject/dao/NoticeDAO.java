package com.koreait.PsnProject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.koreait.PsnProject.dto.NoticeDTO;

@Mapper
public interface NoticeDAO {

    // 공지사항 목록
    List<NoticeDTO> getAllNotices();

    // 공지사항 상세
    NoticeDTO getNoticeById(@Param("id") Long id);

    // 공지사항 등록
    int insertNotice(NoticeDTO notice);

    // 공지사항 수정
    int updateNotice(NoticeDTO notice);

    // 공지사항 삭제
    int deleteNotice(@Param("id") Long id);
}