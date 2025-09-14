package com.aiproject.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
	@GetMapping("/cart")
	public String loginForm(Model model) {
		return "cart";
	}
}
