package com.aiproject.pay;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.aiproject.product.Product;
import com.aiproject.product.ProductRepository;

@Service
public class KakaoPayService {
	
	@Autowired
	ProductRepository pRepo;
	
    private final String adminKey = "2be856aaf26b320ab13144ff7f30874a"; // 카카오페이 어드민 키 <<라고 해서 너무 멀리 돌아왔는데 이거 그냥 카카오 디밸로퍼 앱 어드민 키임 속았음 
    private final String cid = "TC0ONETIME"; // 테스트용 CID 파트너쉽 맺거나 하는거 아닌이상은 테스트 cid 이용
    private final WebClient webClient = WebClient.create("https://kapi.kakao.com");

    public Map<String, Object> readyPayment(String orderId, String userId, int amount) {
        System.out.println("partner_order_id: " + orderId);
        System.out.println("partner_user_id: " + userId);
        System.out.println("item_name: Board 상품");
        System.out.println("quantity: 1");
        System.out.println("total_amount: " + amount);
        orderId = orderId + String.valueOf(System.currentTimeMillis()).substring(6);
        Map<String, Object> response = webClient.post()
                .uri("/v1/payment/ready")
                .header("Authorization", "KakaoAK " + adminKey)	//말이 헤더지 그냥 기본적으로 들어가는 provider랑 adminKey임 (카카오 디밸로퍼에있음 페이 디벨로퍼 x)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("cid", cid)
                        .with("partner_order_id", orderId)	 // --> 이거 고민좀했는데 "유니크값이 들어가야함"안그러면 카카오페이에서 오류로 컷 그래서 거래번호 or 사용자이름 + 시간 250910 이런식으로 만들면 좋을거같음
                        .with("partner_user_id", userId)	
                        .with("item_name", "Board 상품") // -> 상품이름
                        .with("quantity", "1")
                        .with("total_amount", String.valueOf(amount))
                        .with("vat_amount", "0")	// 세금 (정확히 뭐였는지 모르겠는데 암튼 세금 -> 부가세같은)
                        .with("tax_free_amount", "0") // 세금 (같음)
                        .with("approval_url", "http://localhost:9090/payment/approve")	// 성공시 접속 url	//카카오 디벨로퍼(페이 디벨로퍼아님) 에서 웹 플랫폼 추가 (로컬환경에서 할거면 "localhost:9090" 이런식으로만 가능 뒤에 / 붙이면 안됩니다)
                        .with("cancel_url", "http://localhost:9090/payment/cancel")		// 취소시 접속 url
                        .with("fail_url", "http://localhost:9090/payment/fail"))		// 실패시 접속 url
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(body -> new RuntimeException("KakaoPay Error: " + body)))
                .bodyToMono(Map.class)
                .block();

        System.out.println("KakaoPay response: " + response);
        return response;
    }
    
    public int getProductPrice(int productIdx) {
        Optional<Product> opt = pRepo.findById(productIdx);
        return opt.map(Product::getPrice).orElse(0); // 없으면 0 반환
    }
}