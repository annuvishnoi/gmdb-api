package com.galvanize.gmdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.repository.GmdbRepository;

public class GmdbServiceTest {
	
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
	public void test_getMovies_retrunMovieList() {
		Movie expectedMovie = new Movie("The Incredibles", "Brad Bird");
		List<Movie> movies = new ArrayList<>();
		movies.add(expectedMovie);
		
		when(gmdbRepository.findAll()).thenReturn(movies);
		
		List<Movie> actualMovies = gmdbService.getMovies();
		
		assertEquals(1, actualMovies.size());
		
		Movie actualMovie = actualMovies.get(0);
		
		
		
		assertEquals(expectedMovie, actualMovie);
	}
	
}
