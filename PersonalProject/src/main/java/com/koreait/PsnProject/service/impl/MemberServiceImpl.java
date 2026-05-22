package com.koreait.PsnProject.service.impl;

import com.koreait.PsnProject.dao.MemberDAO;
import com.koreait.PsnProject.dto.MemberDTO;
import com.koreait.PsnProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// ... (다른 import)

import java.util.List;
import java.util.Collections;

@Component
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDAO memberDAO;

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