package com.darshanbk812.aiservice.service;

import com.darshanbk812.aiservice.model.Recommendation;
import com.darshanbk812.aiservice.repo.RecommendationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepo recommendationRepo;


    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepo.findByUserId(userId);
    }

    // RecommendationService.java
    public Optional<Recommendation> getActivityRecommendation(String activityId) {
        return recommendationRepo.findByActivityId(activityId);
    }

}
