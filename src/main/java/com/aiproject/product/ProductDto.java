package com.aiproject.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	private Integer productIdx;
	
	private String productName;
	
	private String productContent;
	
	private String productImg;
	
	private Integer price;
	
	private String category;
	
	private Integer rating;
	
	public ProductDto(Product product) {
		productIdx = product.getProductIdx();
		productName = product.getProductName();
		productContent = product.getProductContent();
		productImg = product.getProductImg();
		price = product.getPrice();
		category = product.getCategory();
		rating = product.getRating();
	}
}
