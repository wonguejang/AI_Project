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

    private final String EXTERNAL_UPLOAD_DIR = "c:/upload";
    // 드롭존에 jpg등 사진으로 들어왔을때의 경로
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File dir = new File(EXTERNAL_UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String fileName = file.getOriginalFilename();
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
}