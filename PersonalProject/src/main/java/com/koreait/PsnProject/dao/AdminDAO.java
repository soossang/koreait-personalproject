package com.koreait.PsnProject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.koreait.PsnProject.dto.UserDTO;
import com.koreait.PsnProject.dto.BoardDTO;

@Mapper
public interface AdminDAO {

    // 전체 회원 조회
    List<UserDTO> getAllUsers();

    // 회원 상세조회
    UserDTO getUserById(@Param("id") Long id);

    // 회원 상태 변경 (정지/활성)
    int updateUserStatus(@Param("id") Long id, @Param("active") boolean active);

    // 게시글 신고 목록 조회
    List<BoardDTO> getReportedBoards();

    // 신고 게시글 삭제
    int deleteReportedBoard(@Param("boardId") Long boardId);

    // 신고 내용 처리 결과 업데이트
    int updateReportStatus(@Param("boardId") Long boardId, @Param("status") String status);
}