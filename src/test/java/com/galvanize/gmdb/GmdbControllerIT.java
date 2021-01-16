package com.galvanize.gmdb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.repository.GmdbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GmdbControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    GmdbRepository gmdbRepository;

    private String moviePath = "src/test/resources/movies.json";

    @BeforeEach
    public void setUp() {
        gmdbRepository.deleteAll();
    }

    @Test
    public void test_getmovies_return204_whenNoMovies() throws Exception {
        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_getmovies_return200_withDetails() throws Exception {
        List<Movie> allMovies = moviesContent();
        Movie movie1 = allMovies.get(0);
        gmdbRepository.saveAll(allMovies);
        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()"). value(7))
                .andExpect(jsonPath("$.data[0]").value(movie1));
    }

    private List<Movie> moviesContent() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File moviesFile = new File(moviePath);
        List<Movie> movies = mapper.readValue(moviesFile, new TypeReference<ArrayList<Movie>>() {});

        return movies;

    }
}
