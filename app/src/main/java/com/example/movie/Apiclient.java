package com.example.movie;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiclient {

    private static Retrofit retrofit;
    static Retrofit getClient() {
        // Create a logging interceptor to log the body of the responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Build the OkHttpClient and attach the interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // Build the Retrofit instance
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/") // Base URL of TMDB
                    .addConverterFactory(GsonConverterFactory.create()) // Convert JSON to Java objects
                    .client(client) // Attach OkHttpClient
                    .build();
        }

        return retrofit;
    }}