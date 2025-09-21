package com.aiproject.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	//댓글 작성일 기준 역순정렬
	List<Reply> findByProduct_ProductIdxOrderByWriteDateDesc(Integer productIdx);
	
}