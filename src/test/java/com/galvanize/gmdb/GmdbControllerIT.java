package com.galvanize.gmdb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.exception.GmdbMissingStarException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.repository.GmdbRepository;
import com.galvanize.gmdb.util.MovieTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GmdbControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    GmdbRepository gmdbRepository;

    private List<Movie> allMovies;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() throws IOException {
        gmdbRepository.deleteAll();
        allMovies = MovieTestUtil.moviesContent();
        mapper = new ObjectMapper();
    }

    @Test
    public void test_getmovies_return204_whenNoMovies() throws Exception {
        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_getmovies_return200_withDetails() throws Exception {
        Movie movie1 = allMovies.get(0);
        gmdbRepository.saveAll(allMovies);
        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()"). value(7))
                .andExpect(jsonPath("$.data[0]").value(movie1));
    }

    @Test
    public void test_getMovieByTitle_return200_withMovieDetail() throws Exception {
        gmdbRepository.saveAll(allMovies);
        mockMvc.perform(get("/api/movies/{title}", "Superman Returns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(allMovies.get(1)));
    }

    @Test
    public void test_getMovieByTitle_return404withMessage_whenNoMovie() throws Exception {
        gmdbRepository.saveAll(allMovies);
        mockMvc.perform(get("/api/movies/{title}", "Superman Again"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMsg")
                                .value("Superman Again doesn't exist"));
    }

    @Test
    public void test_PostReview() throws Exception {

        Movie supermanMovie = allMovies.get(1);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating(5,""));
        ratings.add(new Rating(3, "some comment"));
        supermanMovie.setRating(ratings);
        gmdbRepository.saveAll(allMovies);

        Rating newRating = new Rating(4, "Terrible");

        mockMvc.perform(put("/api/movies/{title}", "Superman Returns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newRating)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.averageRating").value(4.0));
    }

    @Test
    public void test_PostReview_seeMyComment() throws Exception {
        Movie supermanMovie = allMovies.get(1);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating(5,""));
        ratings.add(new Rating(3, "some comment"));
        supermanMovie.setRating(ratings);
        gmdbRepository.saveAll(allMovies);

        Rating newRating = new Rating(4, "Terrible");

        mockMvc.perform(put("/api/movies/{title}", "Superman Returns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newRating)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Terrible")));
    }

    @Test
    public void test_PostReview_throwException_withoutStar() throws Exception {
        Rating rating = new Rating(null, "Great movie");

        mockMvc.perform(put("/api/movies/{title}", "Superman Returns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rating)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMsg").value("Please enter star for your rating."));
    }
}
