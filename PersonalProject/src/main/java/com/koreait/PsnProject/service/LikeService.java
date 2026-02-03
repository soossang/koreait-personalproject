package com.koreait.PsnProject.service;

import com.koreait.PsnProject.dto.LikeDTO; // DTO import 추가

public interface LikeService {
    // 파라미터를 DTO로 받고, 반환 타입을 boolean으로 변경
    boolean toggleLike(LikeDTO likeDTO);
    int getLikeCount(Long hospitalId);
}