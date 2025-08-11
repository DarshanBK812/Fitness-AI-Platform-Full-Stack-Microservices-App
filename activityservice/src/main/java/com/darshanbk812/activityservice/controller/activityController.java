package com.darshanbk812.activityservice.controller;

import com.darshanbk812.activityservice.client.UserClient;
import com.darshanbk812.activityservice.dto.ActivityRequest;
import com.darshanbk812.activityservice.dto.ActivityResponse;
import com.darshanbk812.activityservice.service.ActivityService.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class activityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserClient userClient;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request ,@RequestHeader("X-User-ID") String userId ){
        if(userId != null){
            request.setUserId(userId);
        }
        return ResponseEntity.ok(activityService.trackActivity(request));
    }
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID") String userId ){
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String activityId ){
        return ResponseEntity.ok(activityService.getActivity(activityId));
    }



    @GetMapping("/validateUser/{userId}")
    public Boolean validateUser(@PathVariable String userId){

        return activityService.validateUser(userId);

    }

    @GetMapping("/msg")
    public String msg(){
        return "activity service  :->" ;
    }

}
