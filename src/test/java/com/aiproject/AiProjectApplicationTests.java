package com.aiproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aiproject.cart.CartRepository;
import com.aiproject.member.MemberRepository;
import com.aiproject.product.ProductRepository;
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
	
}
