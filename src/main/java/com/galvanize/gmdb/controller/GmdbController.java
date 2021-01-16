package com.galvanize.gmdb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies")
public class GmdbController {

	@GetMapping
	public ResponseEntity<List<String>> getMovies() {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
