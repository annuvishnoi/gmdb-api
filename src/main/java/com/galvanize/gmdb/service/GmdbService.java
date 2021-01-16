package com.galvanize.gmdb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.galvanize.gmdb.model.Movie;
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

    public Movie getMovieByTitle(String anyString) {
		return new Movie();
    }
}
