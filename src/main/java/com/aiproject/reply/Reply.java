package com.aiproject.reply;

import java.time.LocalDateTime;

import com.aiproject.member.Member;
import com.aiproject.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reply {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="reply")
	@SequenceGenerator(name="reply", sequenceName="seq_reply_rno", allocationSize=1)
	private Integer rno;
	
	//컬럼길이 추가
	@Column(length = 2000)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="member_idx", referencedColumnName = "member_idx")
	@JoinColumn(name = "memberEmail")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="productIdx")
	private Product product; 
	
	private LocalDateTime writeDate;
}
