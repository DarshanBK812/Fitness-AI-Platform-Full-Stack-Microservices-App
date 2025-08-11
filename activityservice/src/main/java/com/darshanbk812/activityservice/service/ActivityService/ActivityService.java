package com.darshanbk812.activityservice.service.ActivityService;

import com.darshanbk812.activityservice.client.UserClient;
import com.darshanbk812.activityservice.dto.ActivityRequest;
import com.darshanbk812.activityservice.dto.ActivityResponse;
import com.darshanbk812.activityservice.model.Activity;
import com.darshanbk812.activityservice.repo.ActivityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityService {

    @Autowired
    private ActivityRepo repo;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest request) {

        log.info("Inside actvity service layer");

        if(!userClient.validateUser(request.getUserId())){
           throw new RuntimeException("User is not present");
        }
        log.info("Calling User validation API for userId");

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurn(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

          Activity savedActivity =  repo.save(activity);

          //publish to rabbitmq for AI Processing
        try {
            rabbitTemplate.convertAndSend(exchange , routingKey , savedActivity);
        }catch (Exception e){
            log.error("Failed to publish activity to RabbitMq {}", e.getMessage());
        }

          return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity){

        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(activity.getId());
        activityResponse.setUserId(activity.getUserId());
        activityResponse.setType(activity.getType());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setCaloriesBurn(activity.getCaloriesBurn());
        activityResponse.setStartTime(activity.getStartTime());
        activityResponse.setAdditionalMetrics(activity.getAdditionalMetrics());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());

        return  activityResponse;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities= repo.findByUserId(userId);
        return activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivity(String activityId) {
        return repo.findById((activityId))
                .map(this::mapToResponse).orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    public Boolean validateUser(String userId) {

        log.info("Validating userId" , userId) ;

        List<Activity>  activity = repo.findByUserId(userId);
        if(activity == null && activity.isEmpty() ){
            log.warn("activity is null or activity is empty" , activity);
        }

        return activity != null && !activity.isEmpty();
    }
}
