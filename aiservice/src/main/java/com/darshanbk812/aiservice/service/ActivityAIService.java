package com.darshanbk812.aiservice.service;

import com.darshanbk812.aiservice.model.Activity;
import com.darshanbk812.aiservice.model.Recommendation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ActivityAIService {

    @Autowired
    private  GeminiService geminiService;

    public Recommendation generateAiResponse(Activity activity){
        String promt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(promt);
        log.info("RESPONSE FROM AI : {}" , aiResponse);
        return  processAiResponse(activity , aiResponse);
    }

    public Recommendation processAiResponse(Activity activity , String aiResponse){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(aiResponse);

            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\```" , "")
                    .trim();

//            log.info("PASSED RESPONSE FROM AI: {}" , jsonContent);
        JsonNode analysisJson = objectMapper.readTree(jsonContent);
        JsonNode analysisNode = analysisJson.path("analysis");
        StringBuilder fullAnalysis = new StringBuilder();
        addAnalysisSecition(fullAnalysis, analysisNode , "overall" , "Overall");
        addAnalysisSecition(fullAnalysis, analysisNode , "pace" , "Pace");
        addAnalysisSecition(fullAnalysis, analysisNode , "heartRate" , "heart Rate");
        addAnalysisSecition(fullAnalysis, analysisNode , "caloriesBurned" , "Calories");

        List<String> improvements = extractImprovements(analysisJson.path("improvements"));
        List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
        List<String> safety = extractSafety(analysisJson.path("safety"));

        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                .recommendation(fullAnalysis.toString().trim())
                .improvements(improvements)
                .suggestions(suggestions)
                .saftey(safety)
                .createdAt(LocalDateTime.now())
                .build();

        } catch (Exception e) {
            e.printStackTrace();
            return  CreateDefaultRecomendation(activity);
        }

    }

    private Recommendation CreateDefaultRecomendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                // a generic fallback message
                .recommendation("Unable to generate AI recommendation at this time. Please try again later.")
                // empty lists so your client can handle them safely
                .improvements(Collections.emptyList())
                .suggestions(Collections.emptyList())
                .saftey(Collections.emptyList())
                .createdAt(LocalDateTime.now())
                .build();
    }


    private List<String> extractSafety(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if(safetyNode.isArray()){
            safetyNode.forEach(item -> {
                safety.add(item.asText());
            });
        }
        return safety.isEmpty() ?
                Collections.singletonList("Follow genearal saftey guidlines") :
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if(suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s" , workout , description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestions provided") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if(improvementsNode.isArray()){
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String detail = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s" , area , detail));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No specific Improvements") :
                improvements;
    }

    private void addAnalysisSecition(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()){
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                Analyze this fitness activity and provide detailed recommendations in this format:
                
                {
                    "analysis": {
                        "overall": "Overall analysis here",
                        "pace": "Pace analysis here",
                        "heartRate": "Heart rate analysis here",
                        "caloriesBurned": "Calories analysis here"
                    },
                    "improvements": [
                        {
                            "area": "Area name",
                            "recommendation": "Detailed recommendation"
                        }
                    ],
                    "suggestions": [
                        {
                            "workout": "Workout name",
                            "description": "Detailed workout description"
                        }
                    ],
                    "safety": [
                        "Safety point 1",
                        "Safety point 2"
                    ]
                }

                Analyze Activity Data:
                    Activity Type: %s
                    Duration: %d minutes
                    Calories Burned: %d
                    Additional Metrics: %s

                Provide the analysis focusing on performance, improvements, next workout suggestions, and safety tips.
                Ensure the response follows the EXACT JSON format shown above. Do not include any text before or after the JSON object.
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurn(),
                activity.getAdditionalMetrics()
        );
    }

}
