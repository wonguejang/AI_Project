package com.aiproject.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class GeminiApiService {

	private final HttpClient http = HttpClient.newHttpClient();

    @Autowired
    private ObjectMapper om;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.endpoint}")
    private String endpoint;

    // 
    public String refine(String input) throws Exception {
        String url = endpoint + "?key=" + apiKey;

        ArrayNode sysParts = om.createArrayNode().add(om.createObjectNode().put("text", "항상 한국어로만 답변하세요. 2~3문장 이내 마케팅 문구 톤은 유지, 반드시 의류(패션) 상품에 적합한 설명으로만 작성하세요."));
        ObjectNode systemInstruction = om.createObjectNode().set("parts", sysParts);

        ArrayNode userParts = om.createArrayNode().add(om.createObjectNode().put("text", input));
        ObjectNode userContent = om.createObjectNode().put("role", "user").set("parts", userParts);

        ObjectNode body = om.createObjectNode();
        body.set("systemInstruction", systemInstruction);
        body.set("contents", om.createArrayNode().add(userContent));

        HttpRequest req = HttpRequest.newBuilder()
                                     .uri(URI.create(url))
                                     .header("Content-Type", "application/json")
                                     .POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        JsonNode root = om.readTree(resp.body());
        return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
    }
}