package com.aiproject.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	private Integer productIdx;
	
	private String productName;
	
	private Integer price;
	
	private String category;
	
	private Integer rating;
	
	public ProductDto(Product product) {
		productIdx = product.getProductIdx();
		productName = product.getProductName();
		price = product.getPrice();
		category = product.getCategory();
		rating = product.getRating();
	}
}
