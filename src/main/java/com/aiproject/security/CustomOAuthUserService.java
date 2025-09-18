package com.aiproject.security;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.aiproject.member.LoginType;
import com.aiproject.member.Member;
import com.aiproject.member.MemberRepository;

@Service
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository mRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        System.out.println("OAuth2 로그인 시작");

        String accessToken = userRequest.getAccessToken().getTokenValue();
        String regId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("Provider: " + regId);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());

        // 로그인 타입과 사용자 정보 파싱
        LoginType loginType;
        String email;
        String nickname;

        if ("kakao".equalsIgnoreCase(regId)) {
            loginType = LoginType.KAKAO;
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> properties = oAuth2User.getAttribute("properties");
            email = kakaoAccount.get("email").toString();
            nickname = properties.get("nickname").toString();
        } else if ("google".equalsIgnoreCase(regId)) {
            loginType = LoginType.GOOGLE;
            email = oAuth2User.getAttribute("email");
            nickname = oAuth2User.getAttribute("name");
        } else {
            throw new IllegalArgumentException("지원하지 않는 OAuth2 provider: " + regId);
        }

        System.out.println("Parsed User Info -> email: " + email + ", nickname: " + nickname);

        // DB 조회 (이메일 + 로그인 타입)
        Member member = mRepo.findByMemberEmailAndLoginType(email, loginType)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .memberEmail(email)
                            .memberName(nickname)
                            .loginType(loginType)
                            .token(accessToken)
                            .verified(true)
                            .build();
                    return mRepo.save(newMember);
                });

        System.out.println("Member.memberIdx: " + member.getMemberIdx());
        System.out.println("OAuth2 로그인 끝");

        return new CustomOAuth2User(member,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes());
    }
}
