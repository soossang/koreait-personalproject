package com.koreait.PsnProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// [추가] Authentication 관련 import
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService; // UserDetailsService import
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * 비밀번호 암호화 Bean (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("[Security Config] PasswordEncoder Bean 생성됨.");
        return new BCryptPasswordEncoder();
    }

    /**
     * [추가] 인증 공급자(Authentication Provider) Bean 설정
     * UserDetailsService와 PasswordEncoder를 사용하여 실제 인증(비밀번호 비교)을 수행합니다.
     * @param userDetailsService UserDetailsServiceImpl Bean이 자동으로 주입됩니다.
     * @param passwordEncoder 위에서 정의한 BCryptPasswordEncoder Bean이 자동으로 주입됩니다.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        System.out.println("[Security Config] DaoAuthenticationProvider Bean 생성 중..."); // 로그 추가
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // 사용할 UserDetailsService 설정
        provider.setPasswordEncoder(passwordEncoder);     // 사용할 PasswordEncoder 설정
        // provider.setHideUserNotFoundExceptions(false); // 사용자를 못 찾았을 때 BadCredentialsException 대신 UsernameNotFoundException 발생 (선택 사항)
        System.out.println("[Security Config] DaoAuthenticationProvider Bean 생성 완료."); // 로그 추가
        return provider;
    }

    /**
     * [추가 - 선택 사항이지만 명시적 설정 위해] AuthenticationManager Bean 설정
     * 위에서 만든 DaoAuthenticationProvider를 사용하여 AuthenticationManager를 구성합니다.
     * Spring Boot 3.x 에서는 SecurityFilterChain 설정 시 자동으로 구성될 수도 있지만,
     * 명시적으로 정의하여 확실하게 합니다.
     * @param daoAuthenticationProvider 위에서 정의한 DaoAuthenticationProvider Bean이 주입됩니다.
     */
    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) {
        System.out.println("[Security Config] AuthenticationManager Bean 생성 중..."); // 로그 추가
        ProviderManager providerManager = new ProviderManager(daoAuthenticationProvider);
        // 다른 AuthenticationProvider가 있다면 여기에 추가 가능 (예: OAuth2 등)
        System.out.println("[Security Config] AuthenticationManager Bean 생성 완료."); // 로그 추가
        return providerManager;
    }


    /**
     * Security 필터 체인 설정
     * HttpSecurity는 위에서 정의한 AuthenticationManager와 PasswordEncoder를 사용하게 됩니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("[Security Config] SecurityFilterChain 설정 시작...");
        http
            .authorizeHttpRequests(auth -> {
                System.out.println("[Security Config] authorizeHttpRequests 설정 중...");
                auth
                    // ... (기존 경로 권한 설정 동일)
                    .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/uploads/**").permitAll()
                    .requestMatchers("/member/join", "/member/login").permitAll()
                    .requestMatchers("/hospital/list", "/hospital/detail/**").permitAll()
                    .requestMatchers("/notice/**").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers("/board/list", "/board/detail/**").permitAll()
                    .requestMatchers("/board/write").authenticated()
                    .requestMatchers("/member/mypage", "/member/edit").authenticated()
                    .requestMatchers("/hospital/manage").hasAuthority("HOSPITAL")
                    .requestMatchers("/admin/**").hasAuthority("ADMIN") // "ADMIN" 권한 확인
                    .anyRequest().permitAll();
            })
            .formLogin(form -> {
                System.out.println("[Security Config] formLogin 설정 중...");
                form
                    .loginPage("/member/login")
                    .defaultSuccessUrl("/", true) // 성공 시 항상 홈으로
                    .failureUrl("/member/login?error=true") // 실패 URL
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll();
            })
            .logout(logout -> {
                System.out.println("[Security Config] logout 설정 중...");
                logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout", "GET"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll();
            });

        http.csrf(csrf -> csrf.disable());
        System.out.println("[Security Config] SecurityFilterChain 설정 완료.");
        return http.build();
    }
}