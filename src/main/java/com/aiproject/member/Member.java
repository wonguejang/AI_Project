package com.aiproject.member;

import java.util.ArrayList;
import java.util.List;

import com.aiproject.cart.Cart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="member")
	@SequenceGenerator(name="member", sequenceName="seq_member_idx", allocationSize=1)
	private Integer memberIdx;

	@Column(unique = true)
	private String memberEmail;
	
	private String memberName;
	
	//데이터 타입 뭘로할지(enum 괜춘할듯?)
	@Enumerated(EnumType.STRING)
	private LoginType loginType;
	
	@Column(length=500)
	private String token;
	
	
	@OneToMany(mappedBy="member")
	private List<Cart> carts = new ArrayList<>();
}