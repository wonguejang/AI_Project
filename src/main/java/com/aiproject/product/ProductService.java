package com.aiproject.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	@Autowired
	private ProductRepository pRepo;
	
	//상품 전체보기
	public List<ProductDto> findAllProduct() {
		List<ProductDto> products = new ArrayList<>();
		
		products = pRepo.findAll(Sort.by(Sort.Direction.DESC, "productIdx"))
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
		
		return products;
	}
	
	//상품 상세
	public ProductDto findById(Integer idx) {
		
		ProductDto productDto = pRepo.findById(idx).map(ProductDto :: new).orElse(null);
		
		return productDto;
	}
	
	//상풍 등록
	public void insertProduct(String productName, String aiTags, String price, String aiConsulting, String imageUrl) {
		Product product = new Product();
        product.setProductName(productName);
        product.setRating(0);
        product.setCategory(aiTags);
        product.setProductContent(aiConsulting);
        product.setProductImg(imageUrl.replace("/images/", ""));
        product.setPrice(Integer.parseInt(price.replaceAll(",", "")));
        pRepo.save(product);
        
        Path tempPath = Paths.get("c:/temp/" + imageUrl.replace("/images/",""));
	    Path uploadPath = Paths.get("c:/upload/" + imageUrl.replace("/images/",""));  // 최종 경로

	    try {
	        Files.createDirectories(uploadPath.getParent()); // 폴더 없으면 생성
	        Files.move(tempPath, uploadPath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@Transactional
	public void readUpdate(Integer idx) {
		Product product = pRepo.findById(idx).get();
		product.setRating(product.getRating() + 1);
	}
}