package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.HospitalDTO;
import com.koreait.PsnProject.service.HospitalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;	

    // ✅ 병원 목록 조회 (JSON 반환)
    @GetMapping("/hospitals")
    public List<HospitalDTO> getHospitals() {
        return hospitalService.getAllHospitals();
    }
}