package com.darshanbk812.aiservice.model;



import lombok.Data;


import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {


    private String id;
    private String userId;
    private String type;
    private  Integer duration;
    private Integer caloriesBurn;
    private LocalDateTime startTime;
    private Map<String ,Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
