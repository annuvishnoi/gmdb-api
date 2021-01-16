package com.galvanize.gmdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.exception.GmdbMissingStarException;
import com.galvanize.gmdb.exception.GmdbNotFoundException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.service.GmdbService;
import com.galvanize.gmdb.util.MovieTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	@Test
	public void test_getMovieByTitle_return404withMessage_whenNoMovie() throws Exception {
		doThrow(new GmdbNotFoundException("Movie doesn't exist.")).when(gmdbService).getMovieByTitle(anyString());
		mockMvc.perform(get("/api/movies/{title}", "Superman Returns"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMsg").value("Movie doesn't exist."));
	}
	
	@Test
	public void test_PostReview() throws Exception {
		Rating requestDTO = new Rating(4, "Great movie");

		Movie superManMovie = allMovies.get(1);

		when(gmdbService.postRating(anyString(), any(Rating.class))).thenReturn(superManMovie);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/movies/{title}", "Superman Returns")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(requestDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").value(superManMovie));

	}

	@Test
	public void test_PostReview_throwException_withoutStar() throws Exception {
		Rating rating = new Rating(null, "Great movie");

		doThrow(new GmdbMissingStarException("Please enter star for your rating.")).when(gmdbService).postRating(anyString(), any(Rating.class));

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/movies/{title}", "Superman Returns")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(rating)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMsg").value("Please enter star for your rating."));
	}

}
