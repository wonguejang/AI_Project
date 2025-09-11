package com.aiproject.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
	@Id
	private String userId;
	
	private String password;
	
	private String userName;
	
	private String userEmail;
}