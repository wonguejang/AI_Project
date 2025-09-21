package com.aiproject.reply;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDto {
	private Integer rno;
	
	private String content;
	
	private String writer;
	
	private String writeDate;
	
	public ReplyDto(Reply reply) {
		rno = reply.getRno();
		content = reply.getContent();
		writer = reply.getMember().getMemberEmail();
		writeDate = reply.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));	}
}
