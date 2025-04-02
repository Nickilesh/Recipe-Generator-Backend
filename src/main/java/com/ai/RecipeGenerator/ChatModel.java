package com.ai.RecipeGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

@Service
public class ChatModel {

    @Value("${groq.api.key}")
    private String apiKey;  // Secure API key from environment variables

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public ChatModel(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String call(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey); // Secure API key
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestPayload = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "max_tokens", 500
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                API_URL, HttpMethod.POST, requestEntity, Map.class
        );

        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseEntity.getBody().get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return message != null ? message.getOrDefault("content", "No response").toString() : "No response from Groq AI.";
        }

        return "No response from Groq AI.";
    }
}
