package com.movieapp.catalog.services;

import com.movieapp.catalog.model.Rating;
import com.movieapp.catalog.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000")
    }
          )
    public UserRating getUserRating(@PathVariable String userId) {
        return restTemplate.getForObject("http://rating-data-service/rating/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(@PathVariable String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserRating(Arrays.asList(
                new Rating("0", 0)
        ));
        return userRating;
    }
}

