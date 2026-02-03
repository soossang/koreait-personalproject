package com.koreait.PsnProject.controller;

import com.koreait.PsnProject.dto.BoardDTO;
import com.koreait.PsnProject.dto.MemberDTO;
import com.koreait.PsnProject.service.BoardService;
import com.koreait.PsnProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService; // 사용자 정보 조회를 위해 주입

    // --- [추가] 404 오류 해결을 위해 /list 매핑 추가 ---
    @GetMapping("/list")
    public String boardList(Model model) {
        // (BoardService에 페이징 기능이 없다면 getAllPosts() 같은 메소드 사용)
        // (이전 코드 기준으로 페이징(start, limit)이 있었으나, 지금은 단순 목록으로 가정)
        List<BoardDTO> boards = boardService.getAllPosts(); // 👈 이 메소드는 BoardService에 있어야 합니다.
        model.addAttribute("boards", boards);
        return "board/board_list"; // -> layout.html을 통해 렌더링
    }

    // --- [추가] 글쓰기 폼 이동 메소드 ---
    @GetMapping("/write")
    public String writeForm() {
        return "board/board_write"; // -> layout.html을 통해 렌더링
    }

    // --- [추가] 글쓰기 처리 메소드 ---
    @PostMapping("/write")
    public String write(BoardDTO boardDTO, Authentication authentication, RedirectAttributes redirectAttributes) {
        
        // 1. 로그인 인증 정보 확인
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal().toString())) {
             redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/member/login";
        }
        
        try {
            // 2. 사용자 이름(username)으로 MemberDTO 조회
            String username = authentication.getName();
            MemberDTO member = memberService.findByUsername(username);
            if (member == null) {
                 throw new UsernameNotFoundException("글쓰기 오류: 사용자 정보를 찾을 수 없습니다.");
            }
            
            // 3. BoardDTO에 작성자 ID(authorId) 설정 (MemberDTO의 ID가 int memberId라고 가정)
            boardDTO.setAuthorId((long) member.getMemberId()); 
            
            // 4. 게시글 작성
            boardService.writePost(boardDTO); // 👈 BoardService에 있어야 합니다.
            
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 등록되었습니다.");
            return "redirect:/board/list";
            
        } catch (Exception e) {
             System.err.println("글쓰기 처리 중 오류 발생: " + e.getMessage());
             e.printStackTrace();
             redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록 중 오류가 발생했습니다.");
             return "redirect:/board/write";
        }
    }


    /**
     * [수정] 게시글 상세 보기
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, Authentication authentication) {
        BoardDTO board = boardService.getPostById(id);
        model.addAttribute("board", board);

        // [추가] 현재 로그인한 사용자 정보 전달 (권한 확인용)
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            MemberDTO currentUser = memberService.findByUsername(username);
            if (currentUser != null) {
                model.addAttribute("currentUserId", (long) currentUser.getMemberId());
            }
        }
        return "board/board_detail"; // -> layout.html을 통해 렌더링됨
    }

    /**
     * [신규] 수정 폼 이동
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        BoardDTO board = boardService.getPostById(id);

        // [권한 검사]
        if (!isAuthor(authentication, board.getAuthorId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/board/list";
        }

        model.addAttribute("board", board);
        return "board/board_edit"; // -> layout.html을 통해 board_edit.html 렌더링
    }

    /**
     * [신규] 수정 처리
     */
    @PostMapping("/edit")
    public String edit(BoardDTO boardDTO, Authentication authentication, RedirectAttributes redirectAttributes) {
        // [권한 검사] (DB에서 원본 게시글 정보를 가져와서 authorId를 확인)
        BoardDTO originalBoard = boardService.getPostById(boardDTO.getId());
        if (!isAuthor(authentication, originalBoard.getAuthorId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/board/list";
        }

        try {
            boardService.updatePost(boardDTO); 
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
            return "redirect:/board/detail/" + boardDTO.getId();
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "수정 중 오류가 발생했습니다.");
            return "redirect:/board/edit/" + boardDTO.getId();
        }
    }

    /**
     * [수정] 게시글 삭제
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        
        // [권한 검사]
        BoardDTO board = boardService.getPostById(id);
        if (!isAuthor(authentication, board.getAuthorId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
            return "redirect:/board/list";
        }

        try {
             boardService.deletePost(id);
             redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
        } catch (Exception e) {
             redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제 중 오류 발생");
             e.printStackTrace();
        }
        return "redirect:/board/list";
    }
    
    /**
     * [권한 검사 헬퍼 메소드]
     */
    private boolean isAuthor(Authentication authentication, Long authorId) {
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal().toString())) {
            return false;
        }
        String username = authentication.getName();
        MemberDTO currentUser = memberService.findByUsername(username);
        
        return currentUser != null && authorId.equals((long) currentUser.getMemberId());
    }
}