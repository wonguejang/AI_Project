package com.aiproject.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService mSvc;
	@Autowired
	MemberRepository mRepo;
	
	//로그인화면
	@GetMapping("/login")
	public String loginForm(Model model) {
		return "login";
	}
	
	//회원가입화면
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("memberCreateForm", new MemberCreateForm());
		return "signup";
	}
	
	//이메일 중복 확인
	@GetMapping("/checkEmail")
	@ResponseBody
	public Map<String, Boolean> checkEmail(@RequestParam("email") String email){
		boolean exists = mRepo.countByMemberEmail(email) > 0 ;
		
		Map<String, Boolean> result = new HashMap<>();
		
		result.put("success", exists);
		
		return result;
	}
	
	//회원가입처리
	@PostMapping("/signup_form")
	public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult br, RedirectAttributes rttr) {
		if(br.hasErrors()) {
			return "signup";
		}

		if(!memberCreateForm.getMemberPw1().equals(memberCreateForm.getMemberPw2())) {
			br.rejectValue("memberPw2", "passwordIncorect중x", "2개의 비밀번호가 일치하지 않음");
			return "signup_form";
		}
		
		String id = memberCreateForm.getMemberEmail();
		String pw = memberCreateForm.getMemberPw1();
		String name = memberCreateForm.getMemberName();
		mSvc.register(id, pw, name);
		
		rttr.addFlashAttribute("signup_message", "입력하신 이메일로 인증 메일을 발송했습니다. 메일함을 확인해주세요.");
		return "redirect:/member/login";
	}
	
	//회원가입 후 아이디를 사용할 수 있게 처리
	@GetMapping("/signup/secu")
	public String verifyEmail(@RequestParam("token") String token, RedirectAttributes rttr) {
		boolean success = mSvc.verifyByToken(token);

		if(success) {
	        rttr.addFlashAttribute("seungin_message","이메일 인증이 완료되었습니다. 로그인해주세요.");
			return "redirect:/member/login";
		}else {
			rttr.addFlashAttribute("seungin_message", "유효하지 않거나 만료된 인증서입니다.");
			return "redirect:/member/login";
		}
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "main";
	}
}
