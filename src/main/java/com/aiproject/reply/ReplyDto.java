package com.aiproject.reply;

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
		writeDate = reply.getWriteDate().toString();
		writeDate = writeDate.replace("T", " ");
		writeDate = writeDate.substring(0, writeDate.indexOf("."));
	}
}
