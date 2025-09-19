package com.aiproject.member;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aiproject.mail.MailService;

import jakarta.transaction.Transactional;

@Service
public class MemberService {
	@Autowired
	private MemberRepository mRepo;
	@Autowired
	private PasswordEncoder pwEncoder;
	@Autowired
	private MailService mSvc;
	
	//회원가입
	@Transactional
	public Member register(String memberEmail, String memberPw, String memberName) {
		if(mRepo.countByMemberEmail(memberEmail) > 0) {
			throw new IllegalArgumentException("이미 가입된 이메일입니다.");
		}

		//토큰생성
		SecureRandom random = new SecureRandom();
    	byte[] bytes = new byte[32];
		random.nextBytes(bytes);;
		String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
		
		//회원가입처리
		Member m = new Member();
		m.setMemberEmail(memberEmail);
		m.setMemberPw(pwEncoder.encode(memberPw));
		m.setMemberName(memberName);
		m.setLoginType(LoginType.NORMAL);
		m.setVerified(false);
		m.setToken(token);
		//db저장
		mRepo.save(m);
		
		//메일 보내기
		mSvc.sendMail(memberEmail,token);
		
		return m;
	}
	
	@Transactional
	public boolean verifyByToken(String token) {
		Optional<Member> om = mRepo.findByToken(token);
		
		if(om.isPresent()) {
			Member m = om.get();
			//회원 로그인 가능하게 처리
			m.setVerified(true);
			//처리완료됬으니 토큰 지워줌
			m.setToken(null);
			//save 안해도 저장을 한다네?
			return true;
		}
		return false;
	}
}