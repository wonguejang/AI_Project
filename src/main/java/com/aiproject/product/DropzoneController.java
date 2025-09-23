package com.aiproject.product;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DropzoneController {

    private final String EXTERNAL_UPLOAD_DIR = "c:/upload";
    // 드롭존에 jpg등 사진으로 들어왔을때의 경로
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File dir = new File(EXTERNAL_UPLOAD_DIR);
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
    
    // 드롭존에 url형식으로 들어왓을때의 경로 (http)
    @PostMapping("/uploadByUrl")
    @ResponseBody
    public String handleUrlUpload(@RequestParam("fileUrl") String fileUrl) {
        return fileUrl; // URL 그대로 DB 저장x
    }
    
    @PostMapping("/aiTag")
    @ResponseBody
    public String aiTag(@RequestBody Map<String, String> data) {
    	String fileUrl = data.get("fileUrl"); // "/images/download_~년~월~일~시~분.png" 혹은 저장된 이미지 이름 "/images/똥싼바지" 이런식으로 옴
    	String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//    	String 태그값 = 어쩌구Svc(fileName);  //그 ai 학습된 ai에 이미지 보내기 만약 경로 그대로 필요하시면 바로위에 fileNAme은 삭제하셔도 무방하심 리턴값음 스트링값으로 변환해서 보내주셔야합니다.
    	return "태그값";
    }
    
    @PostMapping("/aiConsulting")
    @ResponseBody
    public String aiConsulting(@RequestBody Map<String, String> data) {
    	String inputText = data.get("inputText");
    	// 컨설팅된내용 = 어쩌구Svc(inputText); //마찬가지로 스트링값으로 변환해서 주셔야함.
    	return "컨설팅된내용";
    }
}