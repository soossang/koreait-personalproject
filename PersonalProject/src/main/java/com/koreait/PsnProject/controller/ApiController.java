package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.HospitalDTO;
import com.koreait.PsnProject.dto.LikeDTO;
import com.koreait.PsnProject.dto.ReviewDTO;
import com.koreait.PsnProject.service.HospitalService;
import com.koreait.PsnProject.service.LikeService;
import com.koreait.PsnProject.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LikeService likeService;

    // ✅ 병원 목록 조회 (JSON 반환)
    @GetMapping("/hospitals")
    public List<HospitalDTO> getHospitals() {
        return hospitalService.getAllHospitals();
    }

    // ✅ 특정 병원 좋아요 토글 (AJAX 요청) - 수정된 부분
    @PostMapping("/hospital/{hospitalId}/like")
    public String toggleLike(@PathVariable Long hospitalId, @RequestParam Long memberId) {
        // 1. DTO 객체를 생성하고 setter로 값을 설정합니다.
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setUserId(memberId); // DTO 필드명에 맞게 setUserId 사용
        likeDTO.setHospitalId(hospitalId);

        // 2. 서비스가 DTO를 받아 처리하고, 처리 후의 '좋아요' 상태(boolean)를 반환하도록 합니다.
        boolean isNowLiked = likeService.toggleLike(likeDTO);

        return isNowLiked ? "liked" : "unliked";
    }

    // ✅ 특정 병원의 좋아요 수 조회
    @GetMapping("/hospital/{hospitalId}/likes")
    public int getLikeCount(@PathVariable Long hospitalId) {
        return likeService.getLikeCount(hospitalId); // 서비스 메서드명 일치
    }

    // ✅ 특정 병원의 리뷰 목록 조회
    @GetMapping("/reviews/{hospitalId}")
    public List<ReviewDTO> getReviews(@PathVariable Long hospitalId) {
        return reviewService.getReviewsByHospitalId(hospitalId);
    }

    // ✅ 리뷰 등록 (AJAX로 요청)
    @PostMapping("/review")
    public String addReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.writeReview(reviewDTO);
        return "success";
    }

    // ✅ 리뷰 삭제 (AJAX)
    @DeleteMapping("/review/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return "deleted";
    }
}