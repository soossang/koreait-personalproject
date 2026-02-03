package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.MemberDAO;
import com.koreait.PsnProject.dto.MemberDTO;
import com.koreait.PsnProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder import
import org.springframework.stereotype.Component;
// ... (다른 import)
import jakarta.annotation.PostConstruct; // PostConstruct import

import java.util.List;
import java.util.Collections;

@Component
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDAO memberDAO;

    // [추가] PasswordEncoder 주입 (비밀번호 재설정용)
    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- [임시 코드] 애플리케이션 시작 시 관리자 비밀번호 재설정 ---
    // !!!주의!!!: 비밀번호 재설정 후에는 이 메소드를 반드시 주석 처리하거나 삭제해야 합니다!
    @PostConstruct // 애플리케이션 시작 시 딱 한 번 실행됨
    public void resetAdminPassword() {
        String adminUsername = "testadmin"; // 비밀번호를 재설정할 관리자 아이디
        String newRawPassword = "new_password123"; // 설정하고 싶은 새 비밀번호 (실제 사용할 비밀번호 입력)

        MemberDTO admin = memberDAO.findByUsername(adminUsername);
        if (admin != null) {
            String encodedPassword = passwordEncoder.encode(newRawPassword);
            // MemberDAO에 updatePasswordById 또는 유사 메소드 필요
            // 예시: memberDAO.updatePassword(admin.getMemberId(), encodedPassword); 
            // 직접 SQL 실행 (더 간단) - MemberDAO 인터페이스에 updatePassword 추가 필요
            try {
                // MemberDAO에 updatePassword 메소드가 Long id, String password를 받는다고 가정
                int updatedRows = memberDAO.updatePassword(Long.valueOf(admin.getMemberId()), encodedPassword); 
                if (updatedRows > 0) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("관리자(" + adminUsername + ") 비밀번호가 재설정되었습니다.");
                    System.out.println("새 비밀번호: " + newRawPassword);
                    System.out.println("DB 저장된 해시: " + encodedPassword.substring(0, 15) + "...");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                } else {
                     System.err.println("### 관리자(" + adminUsername + ") 비밀번호 업데이트 실패 (DB 오류)");
                }
            } catch (Exception e) {
                 System.err.println("### 관리자 비밀번호 업데이트 중 예외 발생: " + e.getMessage());
                 e.printStackTrace();
            }
        } else {
            System.err.println("### 관리자(" + adminUsername + ") 계정을 찾을 수 없어 비밀번호를 재설정할 수 없습니다.");
        }
    }
    // --- [임시 코드 끝] ---


    // --- (기존 MemberServiceImpl 코드) ---
    @Override
    public List<MemberDTO> getAllMembers() {
        // TODO: MemberDAO에 getAllMembers() 구현 후 주석 해제
        // return memberDAO.getAllMembers();
        return Collections.emptyList(); // 임시 반환
    }

    @Override
    public MemberDTO getMemberById(Long id) { return memberDAO.getMemberById(id); }
    @Override
    public void registerMember(MemberDTO member) { memberDAO.insertMember(member); }
    @Override
    public void updateMember(MemberDTO member) { memberDAO.updateMember(member); }
    @Override
    public void deleteMember(Long id) { memberDAO.deleteMember(id); }
    @Override
    public MemberDTO findByUsername(String username) { return memberDAO.findByUsername(username); }
    @Override
    public MemberDTO login(String username, String password) { return null; }
}