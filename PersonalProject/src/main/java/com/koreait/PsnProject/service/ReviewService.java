package com.koreait.PsnProject.service;

import com.koreait.PsnProject.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsByHospitalId(Long hospitalId);
    void writeReview(ReviewDTO review);
    void deleteReview(Long id);
}