package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.MemberDTO;
import com.koreait.PsnProject.dto.NoticeDTO;
import com.koreait.PsnProject.service.MemberService; // MemberService 주입 필요
import com.koreait.PsnProject.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder; // SecurityContextHolder import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    // 관리자 ID 조회를 위해 MemberService 주입 (필수!)
    @Autowired
    private MemberService memberService;

    // --- (목록, 상세, 글쓰기 폼, 수정 폼, 수정 처리, 삭제 처리 메소드는 이전과 동일) ---

    @GetMapping("/list")
    public String noticeList(Model model) {
        List<NoticeDTO> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "notice/notice_list";
    }

    @GetMapping("/detail/{id}")
    public String noticeDetail(@PathVariable("id") Long id, Model model) {
        NoticeDTO notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "notice/notice_detail";
    }

    @GetMapping("/write")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String writeForm(Model model) {
        model.addAttribute("noticeDTO", new NoticeDTO());
        return "notice/notice_write";
    }

    /**
     * [수정] 공지사항 작성 처리 (ADMIN만 실행 가능)
     * - 현재 로그인한 관리자의 ID를 조회하여 noticeDTO.setAdminId() 설정
     */
    @PostMapping("/write")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String writeSubmit(@ModelAttribute NoticeDTO noticeDTO, Authentication authentication, RedirectAttributes redirectAttributes) {

        // --- [수정/추가] 작성자(관리자) ID 설정 ---
        String username = authentication.getName(); // 현재 로그인한 관리자 username
        MemberDTO admin = memberService.findByUsername(username); // username으로 관리자 정보 조회

        if(admin != null) {
            // MemberDTO의 ID가 int memberId라고 가정하고 Long으로 변환하여 adminId 설정
            noticeDTO.setAdminId(Long.valueOf(admin.getMemberId()));
        } else {
             // 관리자 정보를 찾지 못한 경우 예외 처리
             System.err.println("### 공지사항 작성 오류: 관리자 정보를 찾을 수 없음 - " + username);
             redirectAttributes.addFlashAttribute("errorMessage", "작성자(관리자) 정보를 찾을 수 없어 등록할 수 없습니다.");
             return "redirect:/notice/write";
        }
        // --- [수정/추가 끝] ---

        try {
            noticeService.insertNotice(noticeDTO); // 이제 adminId 값이 포함된 DTO 전달
            redirectAttributes.addFlashAttribute("successMessage", "공지사항이 등록되었습니다.");
            return "redirect:/notice/list";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 등록 중 오류 발생: " + e.getMessage()); // 오류 메시지 포함
            return "redirect:/notice/write";
        }
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editForm(@PathVariable("id") Long id, Model model) {
        NoticeDTO notice = noticeService.getNoticeById(id);
        model.addAttribute("noticeDTO", notice);
        return "notice/notice_edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editSubmit(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
        try {
            // (참고: 수정 시에는 adminId를 변경하지 않는 것이 일반적)
            noticeService.updateNotice(noticeDTO);
            redirectAttributes.addFlashAttribute("successMessage", "공지사항이 수정되었습니다.");
            return "redirect:/notice/detail/" + noticeDTO.getId();
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 수정 중 오류 발생");
            return "redirect:/notice/edit/" + noticeDTO.getId();
        }
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteSubmit(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            noticeService.deleteNotice(id);
            redirectAttributes.addFlashAttribute("successMessage", "공지사항이 삭제되었습니다.");
            return "redirect:/notice/list";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 삭제 중 오류 발생");
            return "redirect:/notice/list";
        }
    }
}