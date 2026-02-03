package com.koreait.PsnProject.service;

import com.koreait.PsnProject.dto.HospitalDTO;
import java.util.List;

public interface HospitalService {
    List<HospitalDTO> getAllHospitals();
    HospitalDTO getHospitalById(Long id);

    // ✅ 회원 ID로 병원 정보 조회 메서드 선언 추가
    HospitalDTO getHospitalByMemberId(int memberId);

    // ✅ 병원 정보 저장 또는 수정 메서드 선언 추가
    void saveOrUpdateHospital(HospitalDTO hospital);

    // 아래 메서드는 사용하지 않으므로 제거하거나 주석 처리
    // void registerHospital(HospitalDTO hospital);
    // void updateHospital(HospitalDTO hospital);
    // void deleteHospital(Long id);
    // void likeHospital(Long id);
}