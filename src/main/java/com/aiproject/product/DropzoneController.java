package com.aiproject.product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DropzoneController {

    private final String UPLOAD_DIR = "src/main/resources/static/images";
    // resources/static/images안에 파일 자동저장
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String fileName = file.getOriginalFilename();
        File dest = new File(dir, fileName);
        file.transferTo(dest);

        return "/images/" + fileName; // DB에 저장할때 불러오기까지 그냥 고려해서 /images/붙여서 저장 + 컨트롤러에서 안하고 굳이 js에 리턴 하는이유 = 드롭존 이벤트 안에서 바로 보여줄려고
    }
    
    //url로 드롭존영역에 들어오면 호출되는 컨트롤러
    @PostMapping("/uploadByUrl")
    @ResponseBody
    public String handleUrlUpload(@RequestParam("fileUrl") String fileUrl) {
        return fileUrl; // DB에 그대로 저장 (파일저장 없음)
    }
}
