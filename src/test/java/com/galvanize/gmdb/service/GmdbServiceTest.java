package com.galvanize.gmdb.service;

import com.galvanize.gmdb.exception.GmdbMissingStarException;
import com.galvanize.gmdb.exception.GmdbNotFoundException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.repository.GmdbRepository;
import com.galvanize.gmdb.util.MovieTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GmdbServiceTest {
	
	private GmdbService gmdbService;
	
	private GmdbRepository gmdbRepository;

	private List<Movie> allMovies;

	@Captor
	ArgumentCaptor<Movie> movieArgumentCaptor;
	
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
	
	@Test
	public void test_postRating() {
		Movie supermanMovie = allMovies.get(1);
		List<Rating> ratings = new ArrayList<>();
		ratings.add(new Rating(5, "some Review"));
		supermanMovie.setRating(ratings);
		when(gmdbRepository.findById(anyString())).thenReturn(Optional.of(supermanMovie));

		when(gmdbRepository.save(any(Movie.class))).thenReturn(supermanMovie);
		
		Movie actualMovie = gmdbService.postRating("SuperMan Returns", new Rating(3, "Awesome"));
		
		assertEquals(supermanMovie, actualMovie);

		verify(gmdbRepository).save(movieArgumentCaptor.capture());

		Movie updatedMovie = movieArgumentCaptor.getValue();

		assertEquals(2 ,
				updatedMovie.getRating().size());
		assertEquals(4, updatedMovie.getAverageRating());
	}

	@Test
	public void test_postRating_throwException_withoutStar() {

		GmdbMissingStarException exception =
				assertThrows(GmdbMissingStarException.class,
					() -> gmdbService.postRating("SuperMan Returns",
												new Rating(null, "Awesome")));
		assertEquals("Please enter star for your rating.", exception.getErrorMsg());
	}

}
