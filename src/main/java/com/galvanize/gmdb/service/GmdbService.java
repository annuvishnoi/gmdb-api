package com.galvanize.gmdb.service;

import java.util.List;

import com.galvanize.gmdb.exception.GmdbNotFoundException;
import org.springframework.stereotype.Service;

import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.repository.GmdbRepository;

@Service
public class GmdbService {
	
	private GmdbRepository gmdbRepository;
	
	public GmdbService(GmdbRepository gmdbRepository) {
		this.gmdbRepository = gmdbRepository;
	}
	public List<Movie> getMovies() {
		return gmdbRepository.findAll();
	}

    public Movie getMovieByTitle(String title) {
		return gmdbRepository.findById(title)
				.orElseThrow(() -> new GmdbNotFoundException(title + " doesn't exist"));
    }
	public Movie postRating(String string, Rating review) {
		return new Movie();
		
	}
}
