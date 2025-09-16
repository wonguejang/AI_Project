package com.aiproject.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	MemberService mSvc;
	
	@GetMapping("/login")
	public String loginForm(Model model) {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		return "signup";
	}
	
	@PostMapping("/signup_form")
	public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult br) {
		if(br.hasErrors()) {
			return "signup_form";
		}

		if(!memberCreateForm.getMemberPw1().equals(memberCreateForm.getMemberPw2())) {
			br.rejectValue("memberPw2", "passwordIncorect중x", "2개의 비밀번호가 일치하지 않음");
			return "signup_form";
		}
		
		String id = memberCreateForm.getMemberEmail();
		String pw = memberCreateForm.getMemberPw1();
		String name = memberCreateForm.getMemberName();
		mSvc.create(id, pw, name);
		
		return "redirect:/main";
		
	}
	
	
}
