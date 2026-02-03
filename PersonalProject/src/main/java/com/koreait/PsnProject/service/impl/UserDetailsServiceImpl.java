package com.koreait.PsnProject.service.impl; // 또는 service 패키지

import com.koreait.PsnProject.dto.MemberDTO;
import com.koreait.PsnProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // UserDetailsService Bean으로 등록
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberService memberService; // DB 조회를 위해 MemberService 주입

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[Security] loadUserByUsername 진입: " + username);

        // 1. DB에서 사용자 정보 조회
        MemberDTO member = memberService.findByUsername(username);

        // 2. 사용자가 없으면 예외 발생
        if (member == null) {
            System.err.println("[Security] 사용자를 DB에서 찾을 수 없음: " + username);
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        // [로그 강화] DB에서 가져온 정보 상세 로깅 (비밀번호는 일부만)
        String roleFromDB = member.getRole();
        String passwordFromDB = member.getPassword();
        System.out.println("[Security] DB 사용자 정보: Username=" + member.getUsername() + ", Role=" + roleFromDB);
        if (passwordFromDB != null && passwordFromDB.length() >= 10) {
            System.out.println("[Security] DB 암호화된 비밀번호 (일부): " + passwordFromDB.substring(0, 10) + "...");
        } else {
             System.out.println("[Security] DB 비밀번호: " + passwordFromDB); // 짧거나 null인 경우 그대로 출력
        }


        // 3. 사용자의 역할(Role)을 GrantedAuthority 리스트로 변환
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roleFromDB != null && !roleFromDB.trim().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(roleFromDB.trim()));
            System.out.println("[Security] GrantedAuthority 생성: " + roleFromDB.trim());
        } else {
             System.err.println("[Security] 경고: 사용자에게 역할(Role)이 지정되지 않음: " + username);
             throw new UsernameNotFoundException("사용자(" + username + ")에게 역할이 지정되지 않았습니다."); // 역할 없으면 로그인 실패
        }


        // 4. Spring Security UserDetails 객체(User 클래스) 반환
        // User(아이디, ★DB에서_가져온_암호화된_비밀번호★, 권한_리스트)
        // 이 비밀번호가 null이거나 비어있으면 안 됨!
        if (passwordFromDB == null || passwordFromDB.isEmpty()) {
             System.err.println("[Security] 치명적 오류: DB에서 가져온 비밀번호가 비어있습니다! 사용자: " + username);
             throw new UsernameNotFoundException("사용자(" + username + ")의 비밀번호 정보가 올바르지 않습니다.");
        }
        
        System.out.println("[Security] UserDetails 객체 생성 및 반환...");
        return new User(member.getUsername(), passwordFromDB, authorities);
    }
}