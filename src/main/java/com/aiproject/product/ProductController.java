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

import jakarta.servlet.http.HttpSession;

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
	
	//이거 내가했나...? 기억이안나네 --> 형님 하신거에 제가 조회수 내용을 추가를했죠
	@GetMapping("/product/{idx}")
	public String productDetail(@PathVariable("idx") Integer idx, Model model, Principal principal, HttpSession session) {
		//상품 정보 가져오기(idx => 상품 번호)
		ProductDto productDetail = pSvc.findById(idx);
		model.addAttribute("product", productDetail);

		//상품 댓글 가져오기(idx => 상품 번호)
		List<ReplyDto> replyList = rSvc.findByProDuctReply(idx);
		model.addAttribute("replies", replyList);
		
		//로그인 여부 담기
		boolean isLogin = (principal != null);
		model.addAttribute("isLogin", isLogin);
		
		// 조회수
		String key = "reads_product_" + idx;
	    if (session.getAttribute(key) == null) {
	        pSvc.readUpdate(idx);
	        session.setAttribute(key, true);
	    }
		
		return "detail";
	}
	
	//원규 : 해당 상품의 댓글 달기
	@PostMapping("/reply/add")
	@ResponseBody
	public ReplyDto addReply(@RequestParam("productIdx") Integer productIdx, @RequestParam("content") String content, Principal principal) {
		String loginEmail = principal.getName();
		ReplyDto replyDto = rSvc.addReply(productIdx, content, loginEmail);
		
		return replyDto;
	}
	
	@GetMapping("/products/new")
	public String insert() {
		return "insert";
	}
	
	//상품등록
	@PostMapping("/productInsertAction")
	public String productInsertAction(
			// 엔터 잘했죠? 칭찬해주셈
	        @RequestParam("productName") String productName,
	        @RequestParam("aiTags") String aiTags,
	        @RequestParam("price") String price,
	        @RequestParam("aiConsulting") String aiConsulting,
	        @RequestParam("imageUrl") String imageUrl ) {
		
		pSvc.insertProduct(
				productName,
				aiTags,
				price,
				aiConsulting,
				imageUrl
				);
		
		return "redirect:/main";
	}

}
