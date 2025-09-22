package com.aiproject.member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

    public MemberDetailService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	System.out.println("loadUserByUsername 호출됨: " + email);
    	
    	Member member = memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + email));

    	System.out.println("DB에서 찾은 사용자: " + member.getMemberEmail() + " / "+ member.getMemberPw() + " / " + member.getLoginType());
    	
    	List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER")); // 기본 권한

        if (LoginType.ADMIN.equals(member.getLoginType())) {
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 권한
        }
    	
        return User.builder()
                .username(member.getMemberEmail())   // 로그인 아이디
                .password(member.getMemberPw())      // BCrypt 해시된 비밀번호
                .disabled(!member.isVerified())
                .authorities(auths)    
                .build();
    }
	
}
