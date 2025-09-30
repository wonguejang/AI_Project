package com.aiproject.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {
	@Autowired
	CartService cSvc;
	
	@GetMapping("/cart")
	public String cart(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		List<Cart> cartList = cSvc.getCartList(email);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	@PostMapping("/cart/add")
	@ResponseBody
	public Map<String, Object> addCart(@RequestBody Map<String, Integer> request) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if(auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
	            response.put("success", false);
	            response.put("message", "로그인 후 이용 가능합니다.");
	            return response;
	        }
	        String userId = auth.getName();
	        int productIdx = request.get("productId");
	        
	        cSvc.addCart(userId, productIdx);  // 서비스에서 장바구니에 추가
	        response.put("success", true);
	        
	    } catch (Exception e) {
	        if(e instanceof AuthenticationException) {
	            response.put("success", false);
	            response.put("error", "NOT_LOGGED_IN");
	        } else {
	            response.put("success", false);
	        }
	    }
	    return response;
	}
	
	@PostMapping("/cart/delete")
	@ResponseBody
	public void deleteCart(@RequestParam("orderIdx") int orderIdx) {
		cSvc.delCart(orderIdx);
	}
	
	@PostMapping("/cart/update")
	@ResponseBody
	public void updateCart(@RequestParam("orderIdx") int orderIdx, @RequestParam("quantity") int quantity) {
		cSvc.updateCart(orderIdx,quantity);
	}
	
}