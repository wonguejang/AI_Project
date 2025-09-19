package com.aiproject.product;

import java.util.ArrayList;
import java.util.List;

import com.aiproject.cart.Cart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product")
	@SequenceGenerator(name="product", sequenceName="seq_product_idx", allocationSize=1)
	private Integer productIdx;
	
	private String productName;
	
	@Column(length=4000)
	private String productImg;
	
	//상품설명 누락 추가(0919)
	@Column(length=4000)
	private String productContent;
	
	private Integer price;
	
	private String category;
	
	private Integer rating;
	
	@OneToMany(mappedBy="product")
	private List<Cart> carts = new ArrayList<>();
	
}
