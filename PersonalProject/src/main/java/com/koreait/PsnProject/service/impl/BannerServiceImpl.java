package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.BannerDAO;
import com.koreait.PsnProject.dto.BannerDTO;
import com.koreait.PsnProject.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDAO bannerDAO;

    @Override
    public List<BannerDTO> getTopBanners() {
        // ✅ findTopBanners() -> getTop3HospitalsByLikes()로 수정
        return bannerDAO.getTop3HospitalsByLikes();
    }

    @Override
    public void addBanner(BannerDTO banner) {
        // ✅ insert() -> insertBanner()로 수정
        bannerDAO.insertBanner(banner);
    }

    @Override
    public void deleteBanner(Long id) {
        // ✅ delete() -> deleteBanner()로 수정
        bannerDAO.deleteBanner(id);
    }
}