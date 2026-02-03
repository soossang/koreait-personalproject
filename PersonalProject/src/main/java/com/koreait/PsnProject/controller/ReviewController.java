package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.ReviewDTO;
import com.koreait.PsnProject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 특정 병원 리뷰 목록
    @GetMapping("/{hospitalId}")
    public String reviewList(@PathVariable Long hospitalId, Model model) {
        List<ReviewDTO> reviews = reviewService.getReviewsByHospitalId(hospitalId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("hospitalId", hospitalId);
        return "review/review_list";
    }

    // 리뷰 작성 폼
    @GetMapping("/write/{hospitalId}")
    public String writeForm(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("hospitalId", hospitalId);
        return "review/review_write";
    }

    // 리뷰 등록
    @PostMapping("/write")
    public String writeReview(ReviewDTO reviewDTO) {
        reviewService.writeReview(reviewDTO);
        return "redirect:/review/" + reviewDTO.getHospitalId();
    }

    // 리뷰 삭제
    @GetMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId, @RequestParam Long hospitalId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/review/" + hospitalId;
    }
}