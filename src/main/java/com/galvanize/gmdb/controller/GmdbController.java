package com.galvanize.gmdb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvanize.gmdb.model.GmdbResponse;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.service.GmdbService;

@RestController
@RequestMapping("api/movies")
public class GmdbController {

	private GmdbService gmdbService;
	
	public GmdbController(GmdbService gmdbService) {
		this.gmdbService = gmdbService;
	}
	
	
	@GetMapping
	public ResponseEntity<GmdbResponse> getMovies() {
		List<Movie> movies = gmdbService.getMovies();
		if(movies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(new GmdbResponse(movies), HttpStatus.OK);
		}
	}

	@GetMapping("/{title}")
	public ResponseEntity<GmdbResponse> getMovieByTitle(@PathVariable String title) {
		GmdbResponse response = new GmdbResponse(gmdbService.getMovieByTitle(title));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@PutMapping("/{title}")
	public ResponseEntity<GmdbResponse> postRating(@PathVariable String title, @RequestBody Rating review) {
		GmdbResponse response = new GmdbResponse(gmdbService.postRating(title, review));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
