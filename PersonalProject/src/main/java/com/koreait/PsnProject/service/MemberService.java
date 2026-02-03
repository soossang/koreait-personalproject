package com.koreait.PsnProject.service;

import com.koreait.PsnProject.dto.MemberDTO;
import java.util.List;

public interface MemberService {

    List<MemberDTO> getAllMembers();

    MemberDTO getMemberById(Long id);

    void registerMember(MemberDTO member);

    void updateMember(MemberDTO member);

    void deleteMember(Long id);

    // ✅ MemberController에서 마이페이지 정보 조회를 위해 호출하는 메서드 추가
    MemberDTO findByUsername(String username);

    // Spring Security가 로그인 처리를 담당하므로, 이 메서드는 삭제하거나 주석 처리해도 됩니다.
    MemberDTO login(String username, String password);
}