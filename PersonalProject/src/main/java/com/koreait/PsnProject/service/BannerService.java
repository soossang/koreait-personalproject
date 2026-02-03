package com.koreait.PsnProject.service;

import com.koreait.PsnProject.dto.BannerDTO;
import java.util.List;

public interface BannerService {
    List<BannerDTO> getTopBanners();
    void addBanner(BannerDTO banner);
    void deleteBanner(Long id);
}