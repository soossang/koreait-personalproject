package com.koreait.PsnProject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.koreait.PsnProject.dto.BannerDTO;

@Mapper
public interface BannerDAO {

    // 배너 전체 조회 (예: 활성화된 광고만)
    List<BannerDTO> getActiveBanners();

    // 특정 병원의 광고 조회
    List<BannerDTO> getBannerByHospitalId(@Param("hospitalId") Long hospitalId);

    // 배너 등록
    int insertBanner(BannerDTO banner);

    // 배너 수정
    int updateBanner(BannerDTO banner);

    // 배너 삭제
    int deleteBanner(@Param("id") Long id);

    // 좋아요 수 기준 top3 병원 조회 (광고용)
    List<BannerDTO> getTop3HospitalsByLikes();
}