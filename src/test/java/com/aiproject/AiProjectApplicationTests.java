package com.aiproject;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aiproject.cart.Cart;
import com.aiproject.cart.CartRepository;
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
	void isnertTest() {
	    Member member = new Member();
	    member.setMemberEmail("0826shc@naver.com");
	    member.setMemberName("성현철");
	    member.setLoginType(null);
	    member.setToken("token1");
	    mRepo.save(member);
	    
	    Product p1 = new Product();
	    p1.setProductName("바지1");
	    p1.setPrice(300000);
	    p1.setCategory("바지");
	    p1.setRating(4);
	    pRepo.save(p1);

	    Cart c1 = new Cart();
	    c1.setProduct(p1);
	    c1.setMember(member);
	    c1.setQuantity(2);
	    c1.setOrderDate(LocalDateTime.now());
	    cRepo.save(c1);

	    Reply r1 = new Reply();
	    r1.setContent("바지주제에 30만원..?");
	    r1.setWriter(member.getMemberEmail());
	    r1.setWriteDate(LocalDateTime.now());
	    rRepo.save(r1);
	}
}
