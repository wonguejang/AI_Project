//package com.aiproject.ai;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import java.util.stream.StreamSupport;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//public class GeminiApi {
//	
//	private final HttpClient http = HttpClient.newHttpClient();
//
//    @Autowired
//    private ObjectMapper om;
//
//    @Value("${gemini.api.key}")
//    private String apiKey;
//
//    @Value("${gemini.api.endpoint}")
//    private String endpoint;
//	
//
//	public String refine(String input) throws Exception {
//		
//		String systemSet = "항상 한국어로만 답변하세요. 2~3문장 이내 마케팅 문구 톤은 유지";
//		
//		ArrayNode sysParts = om.createArrayNode().add(om.createObjectNode().put("text", systemSet));
//		ObjectNode systemInstruction = om.createObjectNode().set("parts", sysParts);
//		
//		ArrayNode userParts = om.createArrayNode().add(om.createObjectNode().put("text", input));
//		ObjectNode userContent = om.createObjectNode().put("role", "user").set("parts", userParts);
//		
//		ObjectNode body = om.createObjectNode();
//	    body.set("systemInstruction", systemInstruction);
//	    body.set("contents", om.createArrayNode().add(userContent));
//		
//		HttpRequest req = HttpRequest.newBuilder()
//				.uri(URI.create(endpoint))
//				.header("Content-Type", "application/json")
//				.POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();
//		
//		HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
//		
//		JsonNode root = om.readTree(resp.body());
//		
//		return root.has("candidates") && root.get("candidates").isArray()
//	            ? Stream.of(root.get("candidates"))
//	                .flatMap(arr -> StreamSupport.stream(arr.spliterator(), false))
//	                .flatMap(candidate -> StreamSupport.stream(candidate.path("content").spliterator(), false))
//	                .flatMap(content -> StreamSupport.stream(content.path("parts").spliterator(), false))
//	                .map(part -> part.path("text").asText(""))
//	                .filter(text -> !text.isEmpty())
//	                .collect(Collectors.joining())
//	            : "";
//	}
//}