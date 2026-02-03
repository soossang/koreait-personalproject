package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.HospitalDAO;
import com.koreait.PsnProject.dto.HospitalDTO;
import com.koreait.PsnProject.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalDAO hospitalDAO;

    @Override
    public List<HospitalDTO> getAllHospitals() {
        return hospitalDAO.getAllHospitals(null, null);
    }

    @Override
    public HospitalDTO getHospitalById(Long id) {
        return hospitalDAO.getHospitalById(id);
    }

    @Override
    public HospitalDTO getHospitalByMemberId(int memberId) {
        return hospitalDAO.findByMemberId(memberId);
    }

    @Override
    @Transactional
    public void saveOrUpdateHospital(HospitalDTO hospital) {
        // DTO에서 memberId (Long) 가져오기
        Long memberIdLong = hospital.getMemberId();

        // memberId가 null이 아닌지 확인
        if (memberIdLong == null) {
            // memberId가 없으면 로직을 진행할 수 없으므로 예외 처리 또는 로깅
            System.err.println("Error: memberId is null in saveOrUpdateHospital");
            // 혹은 throw new IllegalArgumentException("Member ID cannot be null");
            return;
        }

        // ✅ [수정] Long 타입을 int 타입으로 변환하여 DAO 메서드 호출
        HospitalDTO existingHospital = hospitalDAO.findByMemberId(memberIdLong.intValue());

        if (existingHospital != null) {
            // 기존 정보가 있으면, DTO의 id를 설정하고 update 실행
            // 주의: 폼에서 id를 받지 않았다면 existingHospital의 id를 사용해야 함
            if (hospital.getId() == null) {
                 hospital.setId(existingHospital.getId());
            }
            hospitalDAO.updateHospital(hospital);
        } else {
            // 기존 정보가 없으면, insert 실행
            hospitalDAO.insertHospital(hospital);
        }
    }
}