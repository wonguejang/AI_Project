package com.aiproject.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aiproject.member.LoginType;
import com.aiproject.member.Member;
import com.aiproject.member.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private MemberRepository mRepo;
	
	public CustomUserDetailsService(MemberRepository mRepo) {
		this.mRepo = mRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("여기 호출되냐? " + email);
		Optional<Member> om = mRepo.findByMemberEmail(email);
		
		if(om.isEmpty()) {
			throw new UsernameNotFoundException("User not found " + email);
		}
		Member m = om.get();
		
		List<GrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		LoginType lt = m.getLoginType();
		if(lt != null && lt == LoginType.ADMIN) {
			auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		return new org.springframework.security.core.userdetails.User(m.getMemberEmail(), m.getMemberPw(), m.isVerified(), true, true, true, auths);
	}
	
}