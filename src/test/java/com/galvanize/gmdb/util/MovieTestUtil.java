package com.galvanize.gmdb.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.model.Movie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieTestUtil {

    private static String moviePath = "src/test/resources/movies.json";

    public static List<Movie> moviesContent() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File moviesFile = new File(moviePath);
        List<Movie> movies = mapper.readValue(moviesFile, new TypeReference<ArrayList<Movie>>() {});

        return movies;
    }
}
