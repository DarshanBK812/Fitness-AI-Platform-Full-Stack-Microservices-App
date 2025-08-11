package com.darshanbk812.activityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Configuration
@FeignClient(name = "user-service", path = "/api/users")
public interface UserClient {

    @GetMapping("/msg")
    public String msg();

    @GetMapping("/validate/{userId}")
    public Boolean validateUser(@PathVariable String userId);


}
