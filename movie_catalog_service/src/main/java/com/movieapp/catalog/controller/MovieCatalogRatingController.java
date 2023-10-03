package com.movieapp.catalog.controller;

import com.movieapp.catalog.model.CatalogItem;
import com.movieapp.catalog.model.UserRating;
import com.movieapp.catalog.services.MovieData;
import com.movieapp.catalog.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catrat")
public class MovieCatalogRatingController {

    @Autowired
    private MovieData movieData;
    @Autowired
    private UserRatingInfo userRatingInfo;
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        // getting rating details
        UserRating ratings = userRatingInfo.getUserRating(userId);

        //getting movie details
        List<CatalogItem> catalogList = ratings.getUserRating()
                .stream().map(movie -> movieData.getCatalogItem(movie)).collect(Collectors.toList());
        return catalogList;
    }
}
