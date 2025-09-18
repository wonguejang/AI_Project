package com.aiproject.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String password;

    public void sendMail(String Receiver, String token) {
    	String title = "회원가입 인증 메일";
    	String content = "수락버튼을 누르시면 회원가입 처리가 완료됩니다.";
    	
		String url = "http://localhost:9090/member/signup/secu?token=" + token;
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setFrom(userName);
			helper.setTo(Receiver);
			helper.setSubject(title);
			
			String htmlContent = "<p>" + content + "</p><br/>"
	                + "<a href='" + url + "' style='display:inline-block;padding:10px 20px;"
	                + "font-size:16px;font-weight:bold;color:#ffffff;background-color:#4CAF50;"
	                + "text-decoration:none;border-radius:5px;'>수락하기</a>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("메일전송 성공");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("메일 전송 실패");
		}
    }
}