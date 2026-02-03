package com.koreait.PsnProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.koreait.PsnProject.dto.ReviewDTO;

@Mapper
public interface ReviewDAO {

    // 특정 병원 후기 목록
    List<ReviewDTO> getReviewsByHospitalId(@Param("hospitalId") Long hospitalId);

    // 후기 작성
    int insertReview(ReviewDTO review);

    // 후기 수정
    int updateReview(ReviewDTO review);

    // 후기 삭제
    int deleteReview(@Param("id") Long id);

    // 후기 평균 평점
    Double getAverageRating(@Param("hospitalId") Long hospitalId);
}