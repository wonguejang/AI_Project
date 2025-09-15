package com.aiproject.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {
	//화면의 name에 유효성검사 걸어줘서 검사하기
	//추가 예정 ( 화면 보고 확인 후 작업 )
	
	@NotEmpty(message="이메일은 필수 입니다.")
	private String memberEmail;

	
	
}