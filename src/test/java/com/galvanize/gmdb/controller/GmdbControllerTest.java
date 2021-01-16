package com.galvanize.gmdb.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.galvanize.gmdb.repository.GmdbRepository;
import com.galvanize.gmdb.util.MovieTestUtil;
import org.junit.jupiter.api.BeforeEach;
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

	private List<Movie> allMovies;

	@BeforeEach
	public void setUp() throws IOException {
		allMovies = MovieTestUtil.moviesContent();
	}
	
	@Test
	public void test_getmovies_return204_whenNoMovies() throws Exception {
		
		mockMvc.perform(
				get("/api/movies")
				)
		.andExpect(status().isNoContent());

		verify(gmdbService).getMovies();
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

		verify(gmdbService).getMovies();
	}

	@Test
	public void test_getMovieByTitle_return200_withMovieDetail() throws Exception {
		Movie superManMovie = allMovies.get(1);
		when(gmdbService.getMovieByTitle(anyString())).thenReturn(superManMovie);
		mockMvc.perform(get("/api/movies/{title}", "Superman Returns"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").value(superManMovie));
		verify(gmdbService).getMovieByTitle("Superman Returns");
	}

	//TODO: continue to test 404 scenario after lunch
}
