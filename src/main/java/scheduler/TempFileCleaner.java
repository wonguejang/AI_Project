//package scheduler;
//
//import java.io.File;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TempFileCleaner {
//
//    private final String TEMP_DIR = "c:/upload";
//
//    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
//    public void cleanUp() {
//        File tempFolder = new File(TEMP_DIR);
//        if (tempFolder.exists() && tempFolder.isDirectory()) {
//            for (File file : tempFolder.listFiles()) {
//                long lastModified = file.lastModified();
//                if (System.currentTimeMillis() - lastModified > 60 * 60 * 1000) { 
//                    file.delete();
//                    System.out.println("임시파일 삭제: " + file.getName());
//                }
//            }
//        }
//    }
//}