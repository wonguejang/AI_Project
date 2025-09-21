package com.aiproject.reply;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiproject.member.Member;
import com.aiproject.member.MemberRepository;
import com.aiproject.product.Product;
import com.aiproject.product.ProductRepository;

@Service
public class ReplyService {
	@Autowired
	private ReplyRepository rRepo;
	
	@Autowired
	private ProductRepository pRepo;
	
	@Autowired
	private MemberRepository mRepo;
	
	// 상품의 댓글 가져오기
	public List<ReplyDto> findByProDuctReply(Integer productIdx) {
		
		List<ReplyDto> replyList = rRepo.findByProduct_ProductIdxOrderByWriteDateDesc(productIdx).stream().map(ReplyDto :: new).collect(Collectors.toList());
		
		return replyList;
	}
	
	//댓글 작성
	public ReplyDto addReply(Integer productIdx, String content, String loginEmail) {
		Optional<Product> productOpt = pRepo.findById(productIdx);
	    if (productOpt.isEmpty()) {
	        return null; // 상품 없으면 null
	    }

	    Optional<Member> memberOpt = mRepo.findByMemberEmail(loginEmail);
	    if (memberOpt.isEmpty()) {
	        return null; // 사용자 없으면 null
	    }

	    Reply reply = new Reply();
	    reply.setContent(content);
	    reply.setProduct(productOpt.get());
	    reply.setMember(memberOpt.get());
	    reply.setWriteDate(LocalDateTime.now());

	    return new ReplyDto(rRepo.save(reply));
	}
	
}