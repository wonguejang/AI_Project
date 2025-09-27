package com.aiproject.scheduler;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TempFileCleaner {

    private final String TEMP_DIR = "c:/temp"; // temp 폴더 경로

    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
    public void cleanUp() {
        System.out.println("스케줄러 실행: " + System.currentTimeMillis());
        File tempFolder = new File(TEMP_DIR);
        if (tempFolder.exists() && tempFolder.isDirectory()) {
            for (File file : tempFolder.listFiles()) {
                long lastModified = file.lastModified();
                if (System.currentTimeMillis() - lastModified > 60 * 60 * 1000) { // 1시간지난 파일 삭제
                    if (file.delete()) {
                        System.out.println("임시파일 삭제: " + file.getName());
                    } else {
                        System.out.println("삭제 실패"); // 삭제할 파일없어도 나옴. 
                    }
                }
            }
        }
    }
}