package com.example.movie;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private static final String API_KEY = "488df15f465fc9f7c4c737b577054f5";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.search_view);
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.DKGRAY);

        recyclerView = findViewById(R.id.recycler_view_popular_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list and set it to the RecyclerView
        movieAdapter = new MovieAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(movieAdapter);

        ApiServices apiService = Apiclient.getClient().create(ApiServices.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY);

        Log.d(TAG, "Making API call to fetch popular movies");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(TAG, "onResponse called");
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieResponse.Movie> movies = response.body().getMovies();
                    Log.d(TAG, "API call successful, movies received: " + movies.size());
                    movieAdapter.updateMovies(movies); // Update the adapter with the new data
                } else {
                    Log.e(TAG, "Response unsuccessful or empty. Response code: " + response.code());
                    Log.e(TAG, "Response message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e(TAG, "Error reading error body", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure called");
                Log.e(TAG, "Failed to load movies", t);
            }
        });
    }
}