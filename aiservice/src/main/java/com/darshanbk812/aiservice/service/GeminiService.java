package com.darshanbk812.aiservice.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.Map;
@Service
public class GeminiService {

    private final WebClient webClient;
    private final String endpointWithKey;

    public GeminiService(
            WebClient.Builder builder,
            @Value("${gemini.api.url}") String baseUrlWithPath,
            @Value("${gemini.api.key}") String apiKey
    ) {
        // Build your WebClient (no baseUrl set here)
        this.webClient = builder.build();

        // Pre-construct the full URL you need, including the key
        this.endpointWithKey = baseUrlWithPath + "?key=" + apiKey;
        // e.g. "https://generativelanguage.googleapis.com/v1beta/models/…:generateContent?key=AIza…"
    }

    public String getAnswer(String question) {
        var requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", question)
                        ))
                )
        );

        return webClient.post()
                // Pass the *entire* URL string here:
                .uri(endpointWithKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
