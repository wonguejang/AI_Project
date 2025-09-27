package com.aiproject.ai;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {
	
	@Autowired
    Hugging_apiService haSvc;
	
	@Autowired
	GeminiApiService gaSvc;

    // Tag 라벨호출 실제 작동은 DropjoneController에서 호출되는중 (형님 드롭존컨트롤러 보기 어려워하실거같아서 그냥 형님 보기 좋으시라고 만들어 뒀습니다. 차후 삭제예정) 드롭존js에서 사진이 db에 저장되면 호출
    @GetMapping("/api/tagTest")
    public String tagTest(@RequestBody Map<String, String> data) throws Exception {
        String fileUrl = data.get("fileUrl"); // "/images/파일이름.png"
        // 저장된 경로 다시 c:/upload/이미지.jpg로 바꿔서 서비스 호출
        String filePath = "c:/upload/" + fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        return haSvc.TagLabel(filePath); // 실제 파일 경로 전달
    }
    
    @PostMapping("/api/consultingTest")
    @ResponseBody
    public String aiConconsultingTestsulting(@RequestBody Map<String, String> data) throws Exception {
        String inputText = data.get("inputText");
        String consultingResult = gaSvc.refine(inputText);
        return consultingResult;
    }
}