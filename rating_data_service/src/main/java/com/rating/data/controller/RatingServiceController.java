package com.rating.data.controller;

import com.rating.data.model.Rating;
import com.rating.data.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingServiceController {

    @RequestMapping("/{movieId}")
    public  Rating getRating(@PathVariable  String movieId){
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public  UserRating getUserRating(@PathVariable  String userId){
        List<Rating> ratingsList = Arrays.asList(
                new Rating("1234", 5),
                new Rating("1235", 4),
                new Rating("1236", 3),
                new Rating("1237", 2),
                new Rating("1238", 1)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratingsList);
        return  userRating;
    }
}
