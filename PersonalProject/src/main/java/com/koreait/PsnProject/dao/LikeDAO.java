package com.koreait.PsnProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.koreait.PsnProject.dto.LikeDTO;

@Mapper
public interface LikeDAO {

    // 특정 병원 좋아요 수
    int countLikesByHospital(@Param("hospitalId") Long hospitalId);

    // 사용자가 해당 병원을 좋아요 했는지 여부
    boolean isLiked(@Param("userId") Long userId, @Param("hospitalId") Long hospitalId);

    // 좋아요 추가
    int insertLike(LikeDTO like);

    // 좋아요 취소
    int deleteLike(@Param("userId") Long userId, @Param("hospitalId") Long hospitalId);

    // 사용자의 좋아요 병원 목록
    List<LikeDTO> getLikesByUser(@Param("userId") Long userId);
}
