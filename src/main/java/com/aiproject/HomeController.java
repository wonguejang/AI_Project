package com.aiproject;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
	//로그아웃 url
	static final String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";
	
	@GetMapping("/")
    public String mainPage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        // 기존 일반 로그인 세션 처리 로직이 있다면 여기에 추가 가능 
        // OAuth2 로그인 사용자 처리
        if (oAuth2User != null) {
            Map<String, Object> props = (Map<String, Object>) oAuth2User.getAttribute("properties");
            String nickname = (props != null) ? (String) props.get("nickname") : (String) oAuth2User.getAttribute("name");
            model.addAttribute("name", nickname);
        }
        return "main"; // 혹은 index 페이지
    }

    // 카카오 로그아웃 처리
    @GetMapping("/logout/kakao")
    public String kakaoLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.postForObject(KAKAO_LOGOUT_URL + "?target_id_type=user_id&target_id={id}",
                        null, String.class, "카카오에서 받은 userId");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new SecurityContextLogoutHandler().logout(request, response, authentication);

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
    }
	
}