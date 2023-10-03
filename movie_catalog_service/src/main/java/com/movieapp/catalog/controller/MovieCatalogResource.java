package com.movieapp.catalog.controller;

import com.movieapp.catalog.model.CatalogItem;
import com.movieapp.catalog.model.Movie;
import com.movieapp.catalog.model.Rating;
import com.movieapp.catalog.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating ratings = restTemplate.getForObject("http://rating-data-service/rating/users/"+userId, UserRating.class);

        // get all rated movie ids
        List<Rating> ratingsList = Arrays.asList(
                new Rating("1234", 5),
                new Rating("1235", 4),
                new Rating("1236", 3),
                new Rating("1237", 2),
                new Rating("1238", 1)
        );

        //getting movie details
        List<CatalogItem> catalogList = ratingsList.stream().map(rating -> {

            // ================rest template code starts==================
              Movie movie = restTemplate.getForObject("http://movie-data-service/moviedata/" + rating.getMovieId(), Movie.class);
            // ================reset template code ends===================

            //================ WebClient code starts=======================
          /*  Movie movie = webClientBuilder.build()
                    .get() // http Method
                    .uri("http://localhost:8081/moviedata/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block(); */
            //==================Web Client code ends=======================

            return new CatalogItem(movie.getName(), "test", rating.getRating());
        }).collect(Collectors.toList());
        // Put them all together
        //  return Collections.singletonList(new CatalogItem("Avengers", "Test", 4));
        return catalogList;
    }
}
