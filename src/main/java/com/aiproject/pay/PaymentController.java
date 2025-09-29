package com.aiproject.pay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aiproject.cart.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
		
	@Autowired
	CartService cSvc;
	
    @Autowired
    private KakaoPayService kpSvc;

    @GetMapping("/payment/ready")
    public String ready(Model model) {
    //security Authentication객체 소환()현재 로그인한사람 정보 조회
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String userId = auth.getName();
	
    int totalPrice = cSvc.TotalPrice(userId);
    
    if(totalPrice <= 0) {
        model.addAttribute("errorMsg", "카트에 상품이 없습니다.");
        return "cart";
    }
    
    															//상품아이디, 구매자이름, 총액
    Map<String, Object> kakaoResponse = kpSvc.readyPayment("HcTestItemByu", userId, totalPrice);
    return "redirect:" + kakaoResponse.get("next_redirect_pc_url");
    }
    
    @GetMapping("/payment/onebuy/{idx}")
    public String onebuy(@PathVariable("idx") int idx, HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        int price = kpSvc.getProductPrice(idx);
        // 단건 결제 여부 세션에 저장
        session.setAttribute("oneBuy", true);
        session.setAttribute("price", price);
        Map<String, Object> kakaoResponse = kpSvc.readyPayment("HcTestItemByu단건구매", userId, price);
        return "redirect:" + kakaoResponse.get("next_redirect_pc_url");
    }
    
    @GetMapping("/payment/approve")
    public String approve(HttpSession session, RedirectAttributes rttr, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        int amount = cSvc.TotalPrice(userId);
        //카트초기화
        Boolean oneBuy = (Boolean) session.getAttribute("oneBuy");
        if (oneBuy == null || !oneBuy) {
            cSvc.cartRemoveAll(userId);
            rttr.addFlashAttribute("message", amount + "원 구매 완료했습니다.");
        }
        else {
        	int price = (Integer) session.getAttribute("price");
        	rttr.addFlashAttribute("message", price + "원 구매 완료했습니다.");
        	session.removeAttribute("oneBuy");
        	session.removeAttribute("price");
        }
        return "redirect:/main";
    }
    @RequestMapping("/payment/cancel")
    public String cancel(RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", "결제가 취소되었습니다.");
        return "redirect:/main";
    }
    @RequestMapping("/payment/fail")
    public String fail(RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", "결제에 실패했습니다.");
        return "redirect:/main";
    }
    
    
}