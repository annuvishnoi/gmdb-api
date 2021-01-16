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
	public Movie postRating(String title, Rating requestDTO) {
		Movie movie = getMovieByTitle(title);

		List<Rating> ratings = movie.getRating();
		ratings.add(new Rating(requestDTO.getStar(), requestDTO.getReviewDetails()));
		Double average = ratings.stream().mapToDouble(Rating::getStar)
				.average()
				.orElse(Double.NaN);
		movie.setAverageRating(average);

		Movie updatedMovie = gmdbRepository.save(movie);
		updatedMovie.setAverageRating(average);
		return updatedMovie;
	}
}
