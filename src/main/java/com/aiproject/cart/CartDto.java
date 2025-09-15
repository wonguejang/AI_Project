package com.aiproject.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
	private Integer orderIdx;
	
	private Integer productIdx;
	
	private Integer memberIdx;
	
	private Integer quantity;

	public CartDto(Cart cart) {
		orderIdx = cart.getOrderIdx();
		productIdx = cart.getProduct().getProductIdx();
		memberIdx = cart.getMember().getMemberIdx();
		quantity = cart.getQuantity();
	}
}