package com.darshanbk812.aiservice.service;

import com.darshanbk812.aiservice.model.Activity;
import com.darshanbk812.aiservice.model.Recommendation;
import com.darshanbk812.aiservice.repo.RecommendationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityMessageListener {

    @Autowired
    private ActivityAIService activityAIService;

    @Autowired
    private RecommendationRepo recommendationRepo;



    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {
        log.info("Received activity for Processing : {}", activity.getId());
        try {
//            String recommendation = activityAIService.generateAiResponse(activity);
//            log.info("Generated Recommendation : {}", recommendation);
            Recommendation recommendation = activityAIService.generateAiResponse(activity);
            recommendationRepo.save(recommendation);
        } catch (Exception ex) {
            // log the error and drop the message so it won’t be requeued
            log.error("Failed to process activity {} — skipping to avoid infinite retry", activity.getId(), ex);
        }
    }
}

