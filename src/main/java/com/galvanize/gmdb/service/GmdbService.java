package com.galvanize.gmdb.service;

import com.galvanize.gmdb.exception.GmdbMissingStarException;
import com.galvanize.gmdb.exception.GmdbNotFoundException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.repository.GmdbRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public Movie postRating(String title, Rating rating) {
		if (rating.getStar() == null )
			throw new GmdbMissingStarException("Please enter star for your rating.");

		Movie movie = getMovieByTitle(title);

		List<Rating> ratings = movie.getRating();
		ratings.add(new Rating(rating.getStar(), rating.getReviewDetails()));
		Double average = ratings.stream().mapToDouble(Rating::getStar)
				.average()
				.orElse(Double.NaN);
		movie.setAverageRating(average);

		Movie updatedMovie = gmdbRepository.save(movie);
		updatedMovie.setAverageRating(average);
		return updatedMovie;
	}
}
