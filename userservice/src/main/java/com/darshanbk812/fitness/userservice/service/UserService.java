package com.darshanbk812.fitness.userservice.service;

import com.darshanbk812.fitness.userservice.dto.RegisterRequest;
import com.darshanbk812.fitness.userservice.dto.UserResponse;
import com.darshanbk812.fitness.userservice.model.User;
import com.darshanbk812.fitness.userservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public UserResponse register(RegisterRequest registerRequest) {

        if (userRepo.existsByEmail(registerRequest.getEmail())) {
            log.info("User already exist check the details : {}" , registerRequest.getEmail());
            User existingUser = userRepo.findByEmail(registerRequest.getEmail());
            UserResponse userResponse = new UserResponse();
            userResponse.setId(existingUser.getId());
            userResponse.setKeycloakId(existingUser.getKeycloakId());
            userResponse.setEmail(existingUser.getEmail());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());

            return userResponse;
        }

        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setKeycloakId(registerRequest.getKeycloakId());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        User response = userRepo.save(user);

        UserResponse userResponse = new UserResponse();

        userResponse.setId(response.getId());
        userResponse.setKeycloakId(response.getKeycloakId());
        userResponse.setEmail(response.getEmail());
        userResponse.setPassword(response.getPassword());
        userResponse.setFirstName(response.getFirstName());
        userResponse.setLastName(response.getLastName());
        userResponse.setCreatedAt(response.getCreatedAt());
        userResponse.setUpdatedAt(response.getUpdatedAt());

        return userResponse;
    }


    public UserResponse getUserProfile(String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setKeycloakId(user.getKeycloakId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        userResponse.setCreatedAt(user.getCreatedAt());

        return userResponse;
    }

    public Boolean activityCallingUser(String userId) {
        return userRepo.existsById(userId);
    }

    public Boolean existByUserId(String userId) {
       log.info("Calling USer validation API fro userID: {}" , userId);
        return userRepo.existsByKeycloakId(userId);
    }




}


