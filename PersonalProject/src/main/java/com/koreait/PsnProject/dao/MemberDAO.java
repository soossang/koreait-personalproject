package com.koreait.PsnProject.dao;

import com.koreait.PsnProject.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List; // List import

@Mapper // MyBatis 매퍼 인터페이스임을 명시
public interface MemberDAO {

    /** 회원가입 (role 포함) */
    int insertMember(MemberDTO member);

    /** username으로 회원 조회 (로그인, 중복 확인 등) */
    MemberDTO findByUsername(@Param("username") String username);

    /** id로 회원 조회 */
    MemberDTO getMemberById(@Param("id") Long id);

    /** email로 회원 조회 */
    MemberDTO findByEmail(@Param("email") String email);

    /** 회원 정보 수정 (닉네임, 이메일 등) */
    int updateMember(MemberDTO member);

    /** 비밀번호 변경 (id 기준) */
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    /** 회원 탈퇴 (id 기준) */
    int deleteMember(@Param("id") Long id);

    /** 닉네임 중복 확인 (결과: 0 또는 1) */
    int checkNickname(@Param("nickname") String nickname);

    /** username 중복 확인 (결과: 0 또는 1) */
    int checkUsername(@Param("username") String username);

    /** 전체 회원 목록 조회 */
    List<MemberDTO> getAllMembers();

}