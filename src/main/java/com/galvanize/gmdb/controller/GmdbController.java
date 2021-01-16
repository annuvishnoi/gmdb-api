package com.galvanize.gmdb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvanize.gmdb.model.GmdbResponse;
import com.galvanize.gmdb.model.Movie;
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
	
}
