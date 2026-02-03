package com.koreait.PsnProject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.koreait.PsnProject.dto.BoardDTO;

@Mapper
public interface BoardDAO {
    
    // 게시글 목록 조회 (페이징)
    List<BoardDTO> getBoardList(@Param("start") int start, @Param("limit") int limit);
    
    // 게시글 단건 조회
    BoardDTO getBoardById(@Param("id") Long id);
    
    // 게시글 작성
    int insertBoard(BoardDTO board);
    
    // 게시글 수정
    int updateBoard(BoardDTO board);
    
    // 게시글 삭제
    int deleteBoard(@Param("id") Long id);
    
    // 게시글 전체 개수
    int getTotalCount();
    
    // 작성자별 게시글 조회
    List<BoardDTO> getBoardsByAuthor(@Param("authorId") Long authorId);
}