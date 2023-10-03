package com.movie.data.controller;

import com.movie.data.model.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviedata")
public class MovieDataResource {

    @RequestMapping("{movieId}")
    public Movie getMovieInfo(@PathVariable String movieId) {
        return new Movie(movieId, "Test name1");
    }
}
