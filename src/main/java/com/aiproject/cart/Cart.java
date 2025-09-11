package com.aiproject.cart;

import com.aiproject.member.Member;
import com.aiproject.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cart")
	@SequenceGenerator(name="cart", sequenceName="seq_cart_idx", allocationSize=1)
	private Integer orderIdx;
	
	//제약관계걸어줘야함
	//컬럼명 따로 설정
	@ManyToOne
	@JoinColumn(name="productIdx")
	private Product product;
	
	//제약관계걸어줘야함
	//컬럼명 따로 설정
	@ManyToOne
	@JoinColumn(name="memberIdx")
	private Member member;
	//team branch test
	private Integer quantity;
}
