package com.aiproject.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
	@GetMapping("/login")
	public String loginForm(Model model) {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		return "signup";
	}
	
	
}
