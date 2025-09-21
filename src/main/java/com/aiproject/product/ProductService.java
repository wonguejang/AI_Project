package com.aiproject.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private ProductRepository pRepo;
	
	//상품 전체보기
	public List<ProductDto> findAllProduct() {
		List<ProductDto> products = new ArrayList<>();
		
		products = pRepo.findAll().stream().map(ProductDto :: new).collect(Collectors.toList());
		
		System.out.println("컨트롤러 : " + products.size());
		
		return products;
	}
	
	//상품 상세
	public ProductDto findById(Integer idx) {
		
		ProductDto productDto = pRepo.findById(idx).map(ProductDto :: new).orElse(null);
		
		return productDto;
	}
	
	
	
}