package com.aiproject.ai;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Hugging_apiService {

	@Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.token}")
    private String token;
	
	private final ObjectMapper mapper = new ObjectMapper()
											.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

	public LabelResultDto classiftTopFrom(String pathOrUrl) throws Exception {
		byte[] bytes = ImageLoader.load(pathOrUrl);
		return classifyTop(bytes);
	}
	
	public LabelResultDto classifyTop(byte[] imageBytes) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer "+token);
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Type", "application/octet-stream");
		conn.setConnectTimeout(15000);
		conn.setReadTimeout(60000);
		conn.setDoOutput(true);
		
		try(OutputStream os = conn.getOutputStream()){
			os.write(imageBytes);
		}
		
		int code = conn.getResponseCode();
		InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
		String body = new String(is.readAllBytes());
		if(code < 200 || code >= 300) {
			throw new RuntimeException("HF HTTP : " + code + " / " + body);
		}
		
		LabelResultDto[] arr = mapper.readValue(body, LabelResultDto[].class);
		
		return Arrays.stream(arr).max(Comparator.comparingDouble(r -> r.score))
				.orElse(null);		
		
	}
	// 한글 인코딩
	public String TagLabel(String pathOrUrl) throws Exception {
	    byte[] bytes = ImageLoader.load(pathOrUrl);
	    LabelResultDto result = classifyTop(bytes);

	    if (result != null) {
	        IncodingLabel incodingLabel = new IncodingLabel();
	        return incodingLabel.toKorean(result.label); // 한글 변환 적용
	    }
	    return null;
	}

}
