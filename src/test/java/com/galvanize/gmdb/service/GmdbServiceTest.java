package com.galvanize.gmdb.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.galvanize.gmdb.exception.GmdbNotFoundException;
import com.galvanize.gmdb.util.MovieTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.repository.GmdbRepository;

public class GmdbServiceTest {
	
	private GmdbService gmdbService;
	
	private GmdbRepository gmdbRepository;

	private List<Movie> allMovies;
	
	@BeforeEach
	public void setUp() throws IOException {
		gmdbRepository = mock(GmdbRepository.class);
		gmdbService = new GmdbService(gmdbRepository);
		allMovies = MovieTestUtil.moviesContent();
	}
	
	@Test
	public void test_getMovies_retrunEmptyList() {

		assertTrue(gmdbService.getMovies().isEmpty());
	}
	
	@Test
	public void test_getMovies_retrunMovieList() throws IOException {
		
		when(gmdbRepository.findAll()).thenReturn(allMovies);
		
		List<Movie> actualMovies = gmdbService.getMovies();
		
		assertEquals(7, actualMovies.size());
		
		Movie actualMovie = actualMovies.get(0);
		
		assertEquals(allMovies.get(0), actualMovie);
	}

	@Test
	public void test_getMovieByTitle_returnMovieDetail() {

		Movie supermanMovie = allMovies.get(1);
		when(gmdbRepository.findById(anyString())).thenReturn(Optional.of(supermanMovie));
		assertEquals(supermanMovie,
				gmdbService.getMovieByTitle("Superman Returns"));
	}

	@Test
	public void test_getMovieByTitle_throwException_whenNotFound() {
		when(gmdbRepository.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(GmdbNotFoundException.class,
				() -> gmdbService.getMovieByTitle("Superman Returns"));
	}

}
