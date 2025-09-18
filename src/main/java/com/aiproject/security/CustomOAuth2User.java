package com.aiproject.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.aiproject.member.Member;

// OAuth2 로그인 사용자 정보를 담는 커스텀 클래스
// Member 엔티티 + Spring Security 권한 + OAuth2 속성 값
public class CustomOAuth2User implements OAuth2User {

    private Member member;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomOAuth2User(Member member, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.member = member;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // OAuth2User에서 식별자로 사용하는 값
    @Override
    public String getName() {
        return member.getMemberEmail();
    }

    public String getEmail() {
        return member.getMemberEmail();
    }

    public String getNickname() {
        return member.getMemberName();
    }

    public Member getMember() {
        return member;
    }
}
