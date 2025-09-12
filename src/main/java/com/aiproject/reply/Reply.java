package com.aiproject.reply;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	//uk에 fk가 안걸려요 ㅠㅠ
	//member테이블의 pk가 Integer여야함 (시큐에서 쓰는 findbyid가 Integer 타입...)
	//근데 작성자를 번호로 할 수 없어서 아이디에 uk걸었는데 fk가 안걸림
	//hibernate이 자꾸 id를 바라봐서 uk에 매핑이 안되요...ㅠ
	private String writer;
	
	private LocalDateTime writeDate;
}
