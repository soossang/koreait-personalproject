package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.MemberDTO;
import com.koreait.PsnProject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // application.properties에서 관리자 코드 값 주입
    @Value("${admin.registration.code}")
    private String secretAdminCode;

    // --- 회원가입 폼 보여주기 ---
    @GetMapping("/join")
    public String joinForm(Model model) {
        // th:object를 위해 빈 DTO 전달
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/join";
    }

    // --- 회원가입 처리 ---
    @PostMapping("/join")
    public String join(@ModelAttribute MemberDTO memberDTO, RedirectAttributes redirectAttributes) {

        // 1. 관리자 코드 확인 및 역할(Role) 설정
        if (memberDTO.getAdminCode() != null && !memberDTO.getAdminCode().trim().isEmpty()
            && memberDTO.getAdminCode().equals(secretAdminCode)) {

            memberDTO.setRole("ADMIN"); // DB에 저장될 값: ADMIN
            System.out.println("[INFO] 관리자 계정 생성 시도: " + memberDTO.getUsername());

        } else if (memberDTO.getRole() == null || memberDTO.getRole().isEmpty() || !("USER".equals(memberDTO.getRole()) || "HOSPITAL".equals(memberDTO.getRole()))) {
            // 폼에서 넘어온 값이 USER 또는 HOSPITAL이 아니면 기본값 USER 설정
             memberDTO.setRole("USER");
        }
        // 관리자 코드가 없거나 틀리면 폼에서 넘어온 role (USER 또는 HOSPITAL) 유지

        // 2. 비밀번호 암호화
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        // 3. 회원 정보 저장 (MemberService 호출)
        try {
            memberService.registerMember(memberDTO); // 이 메소드가 role 값을 DB에 저장해야 함
            redirectAttributes.addFlashAttribute("successMessage", "가입이 완료되었습니다.");
            return "redirect:/member/login";
        } catch (Exception e) {
            System.err.println("회원가입 오류: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다. (아이디/이메일 중복 등)");
            // 실패 시 입력값 유지를 위해 다시 전달 (선택 사항)
            // redirectAttributes.addFlashAttribute("memberDTO", memberDTO);
            return "redirect:/member/join";
        }
    }

    // --- 로그인 ---
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    // --- 로그아웃 ---
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // SecurityConfig에서 처리하므로 실제 로직 불필요
        session.invalidate();
        return "redirect:/";
    }

    // --- 마이페이지 ---
    @GetMapping("/mypage")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal().toString())) {
            return "redirect:/member/login";
        }
        String username = authentication.getName();
        try {
             MemberDTO member = memberService.findByUsername(username);
             if (member != null) {
                 model.addAttribute("member", member);
             } else {
                 model.addAttribute("errorMessage", "사용자 정보를 불러올 수 없습니다.");
                 System.err.println("마이페이지 오류: 로그인된 사용자(" + username + ") 정보를 DB에서 찾을 수 없음");
             }
        } catch(UsernameNotFoundException e) {
             model.addAttribute("errorMessage", "사용자 정보를 불러올 수 없습니다.");
             System.err.println("마이페이지 오류: " + e.getMessage());
        } catch (Exception e) {
             model.addAttribute("errorMessage", "오류가 발생했습니다.");
             System.err.println("마이페이지 오류: " + e.getMessage());
             e.printStackTrace();
        }
        return "member/mypage";
    }
}