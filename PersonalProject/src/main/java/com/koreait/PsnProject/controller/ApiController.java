package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.HospitalDTO;
import com.koreait.PsnProject.dto.ReviewDTO;
import com.koreait.PsnProject.service.HospitalService;
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

    // ✅ 병원 목록 조회 (JSON 반환)
    @GetMapping("/hospitals")
    public List<HospitalDTO> getHospitals() {
        return hospitalService.getAllHospitals();
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