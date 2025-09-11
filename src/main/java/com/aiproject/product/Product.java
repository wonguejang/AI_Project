package com.aiproject.product;

import java.util.ArrayList;
import java.util.List;

import com.aiproject.cart.Cart;

import jakarta.persistence.Entity;
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
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product")
	@SequenceGenerator(name="product", sequenceName="seq_product_idx", allocationSize=1)
	private Integer productIdx;
	
	private String productName;
	
	private Integer price;
	
	private String category;
	
	private Integer rating;
	
	@OneToMany(mappedBy="product")
	private List<Cart> carts = new ArrayList<>();
}
