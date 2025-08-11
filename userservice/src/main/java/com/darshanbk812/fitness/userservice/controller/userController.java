package com.darshanbk812.fitness.userservice.controller;

import com.darshanbk812.fitness.userservice.dto.RegisterRequest;
import com.darshanbk812.fitness.userservice.dto.UserResponse;
import com.darshanbk812.fitness.userservice.model.User;
import com.darshanbk812.fitness.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private UserService  userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        return  ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
        return  ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/activityCallingUser/{userId}")
    public Boolean activityCallingUser(@PathVariable String userId){
        return userService.activityCallingUser(userId);
    }

     @GetMapping("/validate/{userId}")
    public Boolean existByUserId(@PathVariable String userId){
        return  userService.existByUserId(userId);
    }

    @GetMapping("/msg")
    public String msg(){
        return "user service";
    }


}
