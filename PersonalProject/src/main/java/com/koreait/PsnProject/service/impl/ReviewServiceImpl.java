package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.ReviewDAO;
import com.koreait.PsnProject.dto.ReviewDTO;
import com.koreait.PsnProject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    @Override
    public List<ReviewDTO> getReviewsByHospitalId(Long hospitalId) {
        // ✅ findByHospitalId() -> getReviewsByHospitalId()로 수정
        return reviewDAO.getReviewsByHospitalId(hospitalId);
    }

    @Override
    public void writeReview(ReviewDTO review) {
        // ✅ insert() -> insertReview()로 수정
        reviewDAO.insertReview(review);
    }

    @Override
    public void deleteReview(Long id) {
        // ✅ delete() -> deleteReview()로 수정
        reviewDAO.deleteReview(id);
    }
}