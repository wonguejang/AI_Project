package com.aiproject.cart;

import java.time.LocalDateTime;

import com.aiproject.member.Member;
import com.aiproject.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	//수량
	private Integer quantity;
	
	//주문일시
	private LocalDateTime orderDate;
	
	
}
