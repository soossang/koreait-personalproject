package com.koreait.PsnProject.service;

import com.koreait.PsnProject.dto.BoardDTO;
import java.util.List;

public interface BoardService {
    List<BoardDTO> getAllPosts();
    BoardDTO getPostById(Long id);
    void writePost(BoardDTO post);
    void updatePost(BoardDTO post);
    void deletePost(Long id);
}