package com.aiproject.member;

import java.util.Collections;
import java.util.List;

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

    	System.out.println("DB에서 찾은 사용자: " + member.getMemberEmail() + " / "+ member.getMemberPw());
    	
    	return User.builder()
    	        .username(member.getMemberEmail())
    	        .password(member.getMemberPw())
    	        .disabled(!member.isVerified())
    	        .authorities(member.getLoginType() == LoginType.ADMIN
    	                     ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
    	                     : Collections.emptyList())
    	        .build();
    }
	
    
    
}
