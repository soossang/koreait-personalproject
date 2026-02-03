package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.HospitalDTO;
import com.koreait.PsnProject.dto.MemberDTO;
import com.koreait.PsnProject.service.HospitalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// [수정] @RequestParam 임포트 추가 (누락된 경우를 대비)
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // 병원 목록 조회
    @GetMapping("/list")
    public String hospitalList(
        // [수정] @RequestParam에 (value = "keyword")를 명시하여 오류를 해결합니다.
        @RequestParam(value = "keyword", required = false) String keyword, 
        Model model
    ) {       
        List<HospitalDTO> hospitalList = hospitalService.getAllHospitals();         
        model.addAttribute("hospitals", hospitalList);        
        model.addAttribute("keyword", keyword); 
        return "hospital/hospital_list"; // -> layout.html을 통해 렌더링
    }

    // 병원 상세 조회
    @GetMapping("/detail/{id}")
    public String hospitalDetail(
        // [수정] 향후 동일한 오류를 방지하기 위해 @PathVariable에도 (value = "id")를 명시합니다.
        @PathVariable(value = "id") Long id, 
        Model model
    ) {
        HospitalDTO hospital = hospitalService.getHospitalById(id);
        model.addAttribute("hospital", hospital);
        return "hospital/hospital_detail";
    }

    /**
     * 병원 정보 관리 페이지를 보여줍니다. (병원 회원 전용)
     */
    @GetMapping("/manage")
    @PreAuthorize("hasRole('HOSPITAL')")
    public String manageHospitalForm(HttpSession session, Model model) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginUser");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        HospitalDTO hospital = hospitalService.getHospitalByMemberId(loginMember.getMemberId());

        if (hospital == null) {
            hospital = new HospitalDTO();
            hospital.setMemberId(Long.valueOf(loginMember.getMemberId()));
        }

        model.addAttribute("hospital", hospital);
        return "hospital/hospital_manage";
    }

    /**
     * 병원 정보 관리 폼에서 제출된 데이터를 처리합니다. (병원 회원 전용)
     */
    @PostMapping("/manage")
    @PreAuthorize("hasRole('HOSPITAL')")
    public String manageHospitalSubmit(
        // [수정] @ModelAttribute에도 (value = "hospital")를 명시하는 것이 좋습니다.
        @ModelAttribute(value = "hospital") HospitalDTO hospital, 
        HttpSession session, 
        RedirectAttributes redirectAttributes
    ) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginUser");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        if (hospital.getMemberId() == null || hospital.getMemberId() != Long.valueOf(loginMember.getMemberId())) {
             redirectAttributes.addFlashAttribute("errorMessage", "잘못된 접근입니다.");
            return "redirect:/"; 
        }

        hospitalService.saveOrUpdateHospital(hospital);

        redirectAttributes.addFlashAttribute("successMessage", "병원 정보가 성공적으로 저장되었습니다.");
        return "redirect:/hospital/manage";
    }
}