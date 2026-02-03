package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.LikeDAO;
import com.koreait.PsnProject.dto.LikeDTO;
import com.koreait.PsnProject.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDAO likeDAO;

    @Override
    public boolean toggleLike(LikeDTO likeDTO) {
        // 1. DTO에서 userId와 hospitalId를 추출하여 현재 좋아요 상태(boolean)를 확인합니다.
        boolean isAlreadyLiked = likeDAO.isLiked(likeDTO.getUserId(), likeDTO.getHospitalId());

        if (isAlreadyLiked) {
            // 2. 이미 좋아요(true) 상태이면, 좋아요를 삭제하고 false를 반환합니다.
            likeDAO.deleteLike(likeDTO.getUserId(), likeDTO.getHospitalId());
            return false;
        } else {
            // 3. 좋아요가 안 된(false) 상태이면, 좋아요를 추가하고 true를 반환합니다.
            likeDAO.insertLike(likeDTO);
            return true;
        }
    }

    @Override
    public int getLikeCount(Long hospitalId) {
        return likeDAO.countLikesByHospital(hospitalId);
    }
}