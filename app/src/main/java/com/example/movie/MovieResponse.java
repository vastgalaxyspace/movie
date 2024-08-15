package com.example.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public static class Movie {
        @SerializedName("title")
        private String title;

        @SerializedName("poster_path")
        private String posterPath;

        public String getTitle() {
            return title;
        }

        public String getPosterPath() {
            return posterPath;
        }
    }
}