package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.BoardDAO;
import com.koreait.PsnProject.dto.BoardDTO;
import com.koreait.PsnProject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDAO boardDAO;

    @Override
    public List<BoardDTO> getAllPosts() {
        // ✅ findAll() -> getBoardList() 로 수정 (페이징 파라미터는 우선 null로 전달)
        // 나중에 페이징 기능 구현 시 이 부분을 수정해야 합니다.
        return boardDAO.getBoardList(0, 10); // 예: 첫 페이지 10개
    }

    @Override
    public BoardDTO getPostById(Long id) {
        // ✅ findById() -> getBoardById() 로 수정
        return boardDAO.getBoardById(id);
    }

    @Override
    public void writePost(BoardDTO post) {
        // ✅ insert() -> insertBoard() 로 수정
        boardDAO.insertBoard(post);
    }

    @Override
    public void updatePost(BoardDTO post) {
        // ✅ update() -> updateBoard() 로 수정
        boardDAO.updateBoard(post);
    }

    @Override
    public void deletePost(Long id) {
        // ✅ delete() -> deleteBoard() 로 수정
        boardDAO.deleteBoard(id);
    }
}