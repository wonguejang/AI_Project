package com.aiproject.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	private Integer memberIdx;
	
	private String memberPw;
	
	private String memberEmail;
	
	private String memberName;
	
	private String loginType;
	
	private String token;
	
	public MemberDto(Member member) {
		memberIdx = member.getMemberIdx();
		memberPw = member.getMemberPw();
		memberEmail = member.getMemberEmail();
		memberName = member.getMemberName();
		//영어 그대로 받을지 한글로 번역해서 받을지 정하기
		loginType = member.getLoginType().toString();
		token = member.getToken();
	}
}