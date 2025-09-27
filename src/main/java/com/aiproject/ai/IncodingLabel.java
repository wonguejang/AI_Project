package com.aiproject.ai;

import java.util.LinkedHashMap;
import java.util.Map;

public class IncodingLabel {
    private static final Map<String, String> keywordMap = new LinkedHashMap<>();

    static {
        // 👕 반팔 상의
        keywordMap.put("t-shirt", "반팔 상의");
        keywordMap.put("polo", "폴로 상의");
        keywordMap.put("shirt", "반팔 상의"); // 일반 셔츠는 보통 반팔 취급

        // 👕 긴팔 상의
        keywordMap.put("sweater", "긴팔 상의");
        keywordMap.put("hoodie", "긴팔 상의");
        keywordMap.put("long sleeve", "긴팔 상의"); // 모델이 이 표현을 안 쓰더라도 대비

        // 👖 반바지
        keywordMap.put("shorts", "반바지");

        // 👖 긴바지
        keywordMap.put("jeans", "청바지");
        keywordMap.put("long pants", "긴바지");

        // 👗 치마
        keywordMap.put("skirt", "반바지");

        // 👔 겉옷
        keywordMap.put("blazer", "겉옷");
        keywordMap.put("coat", "겉옷");
        keywordMap.put("denim jacket", "겉옷");
        keywordMap.put("jacket", "겉옷");
        keywordMap.put("sports jacket", "겉옷");

        // 👗 드레스
        keywordMap.put("dresses", "드레스");
    }

    public String toKorean(String eng) {
        String lower = eng.toLowerCase();
        for (Map.Entry<String, String> e : keywordMap.entrySet()) {
            if (lower.contains(e.getKey())) {
                return e.getValue();
            }
        }
        return eng; // 매핑 실패 시 원문 그대로 반환
    }
}
