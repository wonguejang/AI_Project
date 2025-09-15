package com.aiproject.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	private MemberRepository mRepo;
	@Autowired
	private PasswordEncoder pwEncoder;
	
	//회원가입
	public Member create(String memberEmail, String memberPw, String name) {
		Member m = new Member();
		m.setMemberEmail(memberEmail);
		m.setMemberPw(pwEncoder.encode(memberPw));
		m.setMemberName(memberPw);
		m.setLoginType(LoginType.NORMAL);
		m.setToken("토큰 수정필요");
		mRepo.save(m);
		
		return m;
	}
	
	//중복확인(이메일 인증이라 딱히?)
	
	
	
}