package com.aiproject;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aiproject.cart.Cart;
import com.aiproject.cart.CartRepository;
import com.aiproject.member.LoginType;
import com.aiproject.member.Member;
import com.aiproject.member.MemberRepository;
import com.aiproject.product.Product;
import com.aiproject.product.ProductRepository;
import com.aiproject.reply.Reply;
import com.aiproject.reply.ReplyRepository;

@SpringBootTest
class AiProjectApplicationTests {
	@Autowired
	MemberRepository mRepo;
	@Autowired
	ProductRepository pRepo;
	@Autowired
	CartRepository cRepo;
	@Autowired
	ReplyRepository rRepo;
	
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void insertMemberTest() {
	    Member member = new Member();
	    member.setMemberEmail("0826shc@naver.com");
	    member.setMemberName("성헌철");
	    member.setMemberPw("1234");
	    member.setLoginType(LoginType.NORMAL);
	    member.setToken("token1");
	    mRepo.save(member);
	}

	@Test
	void insertProductTest() {
	    Product p1 = new Product();
	    p1.setProductName("바지1");
	    p1.setPrice(300000);
	    p1.setCategory("바지");
	    p1.setRating(4);
	    p1.setProductImg("이미지url 또는 경로");
	    pRepo.save(p1);
	}

	@Test
	void insertCartTest() {
	    Optional<Member> member = mRepo.findById(2); // Member 먼저 insert된 게 있어야 함
	    Optional<Product> product = pRepo.findById(2);

	    if(member.isPresent() && product.isPresent()) {
	        Cart c1 = new Cart();
	        c1.setMember(member.get());
	        c1.setProduct(product.get());
	        c1.setQuantity(2);
	        c1.setOrderDate(LocalDateTime.now());
	        cRepo.save(c1);
	    }
	}

	@Test
	void insertReplyTest() {
	    Optional<Member> member = mRepo.findById(2);

	    if(member.isPresent()) {
	        Reply r1 = new Reply();
	        r1.setContent("바지주제에 30만원..?");
	        r1.setMember(member.get()); // 연관관계 주입
	        r1.setWriteDate(LocalDateTime.now());
	        rRepo.save(r1);
	    }
	}
	
	//현의 테스트
	@Test
	void isnertTest() {
	    Member m1 = new Member();
	    m1.setMemberEmail("0826shc@naver.com");
	    m1.setMemberName("성현철");
	    m1.setMemberPw("1234");
	    m1.setLoginType(LoginType.NORMAL);
	    m1.setToken("token1");
	    mRepo.save(m1);

	    Member m2 = new Member();
	    m2.setMemberEmail("wonguejang@naver.com");
	    m2.setMemberName("장원규");
	    m2.setMemberPw("5678");
	    m2.setLoginType(LoginType.NORMAL);
	    m2.setToken("token2");
	    mRepo.save(m2);
	    
	    Product p1 = new Product();
	    p1.setProductName("바지1");
	    p1.setPrice(300000);
	    p1.setCategory("바지");
	    p1.setProductImg("바지.jpg");
	    p1.setRating(4);
	    pRepo.save(p1);
	    
	    Product p2 = new Product();
	    p2.setProductName("바지2");
	    p2.setPrice(250000);
	    p2.setCategory("바지");
	    p2.setProductImg("https://shop-phinf.pstatic.net/20240906_58/1725627691965z00Re_JPEG/189626079419204_1360160787.jpg?type=f750_750");
	    p2.setRating(4);
	    pRepo.save(p2);
	    
	    Product p3 = new Product();
	    p3.setProductName("신발1");
	    p3.setPrice(120000);
	    p3.setCategory("신발");
	    p3.setProductImg("https://image2.lotteimall.com/goods/63/63/01/1454016363_L.jpg");
	    p3.setRating(5);
	    pRepo.save(p3);
	    
	    Product p4 = new Product();
	    p4.setProductName("신발2");
	    p4.setPrice(185000);
	    p4.setCategory("신발");
	    p4.setProductImg("https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/5673251626/B.jpg?96000000");
	    p4.setRating(4);
	    pRepo.save(p4);

	    Product p5 = new Product();
	    p5.setProductName("상의1");
	    p5.setPrice(310000);
	    p5.setCategory("상의");
	    p5.setProductImg("상의.jpg");
	    p5.setRating(4);
	    pRepo.save(p5);
	    
	    Cart c1 = new Cart();
	    c1.setProduct(p1);
	    c1.setMember(m1);
	    c1.setQuantity(2);
	    c1.setOrderDate(LocalDateTime.now());
	    cRepo.save(c1);
	    
	    Cart c2 = new Cart();
	    c2.setProduct(p1);
	    c2.setMember(m1);
	    c2.setQuantity(1);
	    c2.setOrderDate(LocalDateTime.now());
	    cRepo.save(c2);
	    
	    Cart c3 = new Cart();
	    c3.setProduct(p1);
	    c3.setMember(m1);
	    c3.setQuantity(3);
	    c3.setOrderDate(LocalDateTime.now());
	    cRepo.save(c3);
	    
	    Cart c4 = new Cart();
	    c4.setProduct(p1);
	    c4.setMember(m1);
	    c4.setQuantity(1);
	    c4.setOrderDate(LocalDateTime.now());
	    cRepo.save(c4);
	    
	    Cart c5 = new Cart();
	    c5.setProduct(p1);
	    c5.setMember(m1);
	    c5.setQuantity(2);
	    c5.setOrderDate(LocalDateTime.now());
	    cRepo.save(c5);

	    Reply r1 = new Reply();
	    r1.setProduct(p1);
	    r1.setContent("구매 잘했다는 생각이 듭니다 아주 좋은 바지에요");
	    r1.setMember(m2);
	    r1.setWriteDate(LocalDateTime.now());
	    rRepo.save(r1);

	    Reply r2 = new Reply();
	    r2.setProduct(p1);
	    r2.setContent("저한테는 조금 부담스러운 가격이었지만 도전해보길 잘한거같아요! 30만원.. 아깝지않다!!");
	    r2.setMember(m1);
	    r2.setWriteDate(LocalDateTime.now());
	    rRepo.save(r2);
	    
	    Reply r3 = new Reply();
	    r3.setProduct(p2);
	    r3.setContent("기장이 제법 기니까 고려해서 구매하세요");
	    r3.setMember(m1);
	    r3.setWriteDate(LocalDateTime.now());
	    rRepo.save(r3);
	    
	    Reply r4 = new Reply();
	    r4.setProduct(p2);
	    r4.setContent("생각보다 재질이 부드러워서 놀랐어요");
	    r4.setMember(m2);
	    r4.setWriteDate(LocalDateTime.now());
	    rRepo.save(r4);
	    
	    Reply r5 = new Reply();
	    r5.setProduct(p3);
	    r5.setContent("가벼와서 신은줄도 모르고 침대로 뛰어버렸어요");
	    r5.setMember(m1);
	    r5.setWriteDate(LocalDateTime.now());
	    rRepo.save(r5);
	    
	    Reply r6 = new Reply();
	    r6.setProduct(p3);
	    r6.setContent("신발이 정말 착용감이 좋네요");
	    r6.setMember(m2);
	    r6.setWriteDate(LocalDateTime.now());
	    rRepo.save(r6);
	    
	    Reply r7 = new Reply();
	    r7.setProduct(p4);
	    r7.setContent("저 댓글알바 아닌데 댓글알바 하는기분이에요");
	    r7.setMember(m1);
	    r7.setWriteDate(LocalDateTime.now());
	    rRepo.save(r7);
	    
	    Reply r8 = new Reply();
	    r8.setProduct(p4);
	    r8.setContent("미끄럽지도 않아서 런닝화로 신어도 괜찮아요");
	    r8.setMember(m2);
	    r8.setWriteDate(LocalDateTime.now());
	    rRepo.save(r8);
	    
	    Reply r9 = new Reply();
	    r9.setProduct(p5);
	    r9.setContent("추워서 이거 입으면 형님이 항상 마음이 추운거라고하시네요");
	    r9.setMember(m1);
	    r9.setWriteDate(LocalDateTime.now());
	    rRepo.save(r9);
	    
	    Reply r10 = new Reply();
	    r10.setProduct(p5);
	    r10.setContent("사실이잖음?");
	    r10.setMember(m2);
	    r10.setWriteDate(LocalDateTime.now());
	    rRepo.save(r10);
	}
}
