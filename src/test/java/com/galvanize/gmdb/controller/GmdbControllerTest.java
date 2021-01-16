package com.galvanize.gmdb.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.service.GmdbService;

@WebMvcTest
public class GmdbControllerTest {
	
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GmdbService gmdbService; 
	
	@Test
	public void test_getmovies_return204_when_no_movies() throws Exception {
		
		mockMvc.perform(
				get("/api/movies")
				)
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void test_getmovies_return200_withDetails() throws Exception {
		Movie movie = new Movie();
		List<Movie> movies = new ArrayList<>();
		movies.add(movie);
		
		when(gmdbService.getMovies()).thenReturn(movies);
		
		mockMvc.perform(
				get("/api/movies")
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.length()").value(1));
		
	}
}
