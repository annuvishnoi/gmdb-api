package com.galvanize.gmdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.repository.GmdbRepository;

public class GmdbServiceTest {
	
	private String moviePath = "src/test/resources/movies.json";
	
	private GmdbService gmdbService;
	
	private GmdbRepository gmdbRepository;
	
	@BeforeEach
	public void setUp() {
		gmdbRepository = mock(GmdbRepository.class);
		gmdbService = new GmdbService(gmdbRepository);
	}
	
	@Test
	public void test_getMovies_retrunEmptyList() {
		assertTrue(gmdbService.getMovies().isEmpty());
	}
	
	@Test
	public void test_getMovies_retrunMovieList() throws IOException {
		
		List<Movie> expectedMovies = moviesContent();
		
		when(gmdbRepository.findAll()).thenReturn(expectedMovies);
		
		List<Movie> actualMovies = gmdbService.getMovies();
		
		assertEquals(7, actualMovies.size());
		
		Movie actualMovie = actualMovies.get(0);
		
		assertEquals(expectedMovies.get(0), actualMovie);
	}
	
	private List<Movie> moviesContent() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		File moviesFile = new File(moviePath);
		List<Movie> movies = mapper.readValue(moviesFile, new TypeReference<ArrayList<Movie>>() {});
		
		return movies;

	}
}
