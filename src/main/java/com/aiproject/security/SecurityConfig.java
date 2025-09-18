package com.aiproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() 
            )
            .formLogin(form -> form
                .loginPage("/member/login") 
                .permitAll()
            )
            .oauth2Login(oauth -> oauth
                .defaultSuccessUrl("/", true)
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
            	            response.sendRedirect("/");
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