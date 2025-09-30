package com.aiproject.product;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aiproject.ai.GeminiApiService;
import com.aiproject.ai.Hugging_apiService;

@Controller
public class DropzoneController {

	private final String TEMP_UPLOAD_DIR = "c:/temp";
    
    @Autowired
    Hugging_apiService haSvc;
    @Autowired
    GeminiApiService gaSvc;
    // 드롭존에 jpg등 사진으로 들어왔을때의 경로
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File dir = new File(TEMP_UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String fileName = file.getOriginalFilename();
        
        // 외부 url로 이미지 업로드시 -> download.png 처럼 저장됨 (덮혀쓰기)라서 유니크하게 저장하기 위해서 timeStamp 이용해서 저장
        // 저장된 사진 바로 사용할것, 근데 만약 저장하는 이미지 이름이 바지1 이런식으로 저장하는데 기존에 있는 이름이면 덮혀씌워짐 예외처리하고싶으면 서비스 불러서 따로 처리해야함 (굳이 구현안했음)
        if (fileName.equals("download.png") || fileName.equals("download.jpg")) {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = String.format("%d년%02d월%02d일%02d시%02d분%02d초",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond()
            );
            fileName = "download_" + timestamp + ".png";
        }
        File dest = new File(dir, fileName);
        file.transferTo(dest);

        return "/images/" + fileName; // 외부 업로드용 URL
    }
    
    //원규 : 사진을 업로드 하면 자동으로 태깅된 값이 들어옴
    @PostMapping("/aiTag")
    @ResponseBody
    public String aiTag(@RequestBody Map<String, String> data) throws Exception {
        String fileUrl = data.get("fileUrl"); // "/images/파일이름.png"
        // 저장된 경로 다시 c:/upload/이미지.jpg로 바꿔서 서비스 호출
        String filePath = "c:/temp/" + fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        return haSvc.TagLabel(filePath); // 실제 파일 경로 전달
    }
    
    //원규 : 간단한 상품설명을 입력하면 ai_api를 호출해 내용을 꾸며줌 
    @PostMapping("/aiConsulting")
    @ResponseBody
    public String aiConsulting(@RequestBody Map<String, String> data) throws Exception {
        String inputText = data.get("inputText");
        String consultingResult = gaSvc.refine(inputText);
        return consultingResult;
    }

    // 현재는 사용하지 않음 확장 가능성을 위해 대기
    // 드롭존에 url형식으로 들어왓을때의 경로 (http)	
    @PostMapping("/uploadByUrl")
    @ResponseBody
    public String handleUrlUpload(@RequestParam("fileUrl") String fileUrl) {
    	return fileUrl; // URL 그대로 DB 저장x
    }
    
}