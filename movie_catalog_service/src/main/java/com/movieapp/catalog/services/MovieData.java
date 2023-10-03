package com.movieapp.catalog.services;

import com.movieapp.catalog.model.CatalogItem;
import com.movieapp.catalog.model.Movie;
import com.movieapp.catalog.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieData {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000")
    })
    public CatalogItem getCatalogItem(Rating rating){
        Movie movie = restTemplate.getForObject("http://movie-data-service/moviedata/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), "test", rating.getRating());

    }

    public CatalogItem getFallbackCatalogItem(Rating rating){
        CatalogItem catalogItem = new CatalogItem("No movie","",0);
        return  catalogItem;
    }
}
