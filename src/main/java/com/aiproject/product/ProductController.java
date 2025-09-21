package com.aiproject.product;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiproject.reply.ReplyDto;
import com.aiproject.reply.ReplyService;

@Controller
public class ProductController {
	@Autowired
	private ProductService pSvc;
	
	@Autowired
	private ReplyService rSvc;
	
	@GetMapping("main")
	public String main(Model model) {
		List<ProductDto> products = pSvc.findAllProduct();
		model.addAttribute("products", products);
		
		return "main";
	}
	
	@GetMapping("/product/{idx}")
	public String productDetail(@PathVariable("idx") Integer idx, Model model, Principal principal) {
		//상품 정보 가져오기(idx => 상품 번호)
		ProductDto productDetail = pSvc.findById(idx);
		model.addAttribute("product", productDetail);

		//상품 댓글 가져오기(idx => 상품 번호)
		List<ReplyDto> replyList = rSvc.findByProDuctReply(idx);
		model.addAttribute("replies", replyList);
		
		//로그인 여부 담기
		boolean isLogin = (principal != null);
		model.addAttribute("isLogin", isLogin);
		
		return "detail";
	}
	
	@PostMapping("/reply/add")
	@ResponseBody
	public ReplyDto addReply(@RequestParam("productIdx") Integer productIdx, @RequestParam("content") String content, Principal principal) {
		String loginEmail = principal.getName();
		ReplyDto replyDto = rSvc.addReply(productIdx, content, loginEmail);
		
		return replyDto;
	}
	
	@GetMapping("insert")
	public String insert() {
		return "insert";
	}
}
