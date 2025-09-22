package com.aiproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import com.aiproject.member.MemberDetailService;

@Configuration
public class SecurityConfig {
	
	@Bean
	public DaoAuthenticationProvider daoAuthProvider(MemberDetailService memberDetailService,
	                                                 PasswordEncoder pwEncoder) {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(memberDetailService);
	    provider.setPasswordEncoder(pwEncoder);
	    return provider;
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider daoAuthProvider) throws Exception {
        http
            .csrf().disable() // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .authenticationProvider(daoAuthProvider)
            .formLogin(form -> form
                .loginPage("/member/login") 
                .loginProcessingUrl("/member/login")
                .usernameParameter("memberEmail")
                .passwordParameter("memberPw")
                .defaultSuccessUrl("/main", true)
                .failureHandler((request, response, exception) -> {
                    if (exception instanceof org.springframework.security.authentication.DisabledException) {
                        // 이메일 인증 안 된 계정
                        response.sendRedirect("/member/login?error=verify");
                    } else {
                        // 일반 로그인 실패 (비번 틀림, 계정 없음 등)
                        response.sendRedirect("/member/login?error");
                    }
                })
                .permitAll()
            )
            .oauth2Login(oauth -> oauth
                .defaultSuccessUrl("/main", true)
            )
            .logout(logout -> logout
            	    .logoutSuccessHandler((request, response, authentication) -> {
            	        if (authentication != null) {
            	            // OAuth2 로그인 사용자 구분
            	            if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            	                String registrationId = oauthToken.getAuthorizedClientRegistrationId();
            	                
            	                if ("kakao".equalsIgnoreCase(registrationId)) {
            	                    // 카카오 로그아웃 API 호출 후 리다이렉트
            	                    String kakaoLogoutUrl = "https://kauth.kakao.com/oauth/logout?client_id="
            	                            + "7403b630ca68755a90efaaab935ef108"
            	                            + "&logout_redirect_uri=" + "http://localhost:9090/";
            	                    response.sendRedirect(kakaoLogoutUrl);
            	                    return;
            	                } 
            	            }
            	            response.sendRedirect("/main");
            	        }
            	    })
            	    .invalidateHttpSession(true)
            	    .deleteCookies("JSESSIONID")
            	);

        return http.build();
    }

    @Bean
    public PasswordEncoder pwEncoder() {
        return new BCryptPasswordEncoder();
    }
}