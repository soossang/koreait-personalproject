package com.koreait.PsnProject.dao;

import com.koreait.PsnProject.dto.HospitalDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface HospitalDAO {

    List<HospitalDTO> getAllHospitals(
        @Param("specialty") String specialty,
        @Param("keyword") String keyword
    );

    HospitalDTO getHospitalById(@Param("id") Long id);

    // ✅ [추가된 부분] 회원 ID로 병원 정보 조회 메서드 선언
    // HospitalServiceImpl에서 이 메서드를 호출하므로 인터페이스에도 정의해야 합니다.
    HospitalDTO findByMemberId(@Param("memberId") int memberId);

    int insertHospital(HospitalDTO hospital);

    int updateHospital(HospitalDTO hospital);

    int deleteHospital(@Param("id") Long id);

    // 아래 메서드들은 필요 시 주석 해제 또는 구현
    // List<HospitalDTO> getTop3HospitalsByLikes();
    // Double getAverageRating(@Param("hospitalId") Long hospitalId);
}