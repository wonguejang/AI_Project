package com.aiproject.member;

import java.util.ArrayList;
import java.util.List;

import com.aiproject.cart.Cart;
import com.aiproject.reply.Reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="member")
	@SequenceGenerator(name="member", sequenceName="seq_member_idx", allocationSize=1)
	@Column(name="member_idx")
	private Integer memberIdx;

	@Column(name="member_email", unique = true)
	private String memberEmail;

	private String memberPw;

	private String memberName;
	
	//데이터 타입 뭘로할지(enum 괜춘할듯?)
	@Enumerated(EnumType.STRING)
	private LoginType loginType;
	
	@Column(length=500)
	private String token;
	//이메일 인증이 완료인지 확인함
	//이메일 인증버튼을 누르면 true로 값이 바뀜(일반 id, pw 로그인 시에만)
	@Column(nullable = false)
	private boolean verified = false;
	
	private boolean enabled = false;
	
	private String verificationToken;
	
	@OneToMany(mappedBy="member")
	private List<Cart> carts = new ArrayList<>();
	
	@OneToMany(mappedBy="member")
	private List<Reply> replies = new ArrayList<>();
}