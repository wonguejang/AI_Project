//package com.aiproject.mail;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/mail")
//public class MailController {
//	
//	private MailService mSvc;
//	
//	public MailController(MailService mSvc) {
//		this.mSvc = mSvc;
//	}
//	
//	@PostMapping("/send")
//	public Map<String, Object> sendMail(@RequestParam("email") String email, String token){
//		Map<String, Object> result = new HashMap<>();
//		try {
//			mSvc.sendMail(email, token);
//			result.put("success", true);
//		}catch(Exception e) {
//			e.printStackTrace();
//			result.put("success", false);
//		}
//		return result;
//	}
//}

// 아직 사용하지 않음