package com.example.movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieResponse.Movie> movieList;
    private static final String TAG = "MovieAdapter";

    public MovieAdapter(Context context, List<MovieResponse.Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieResponse.Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());

        // Load image using Glide
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + movieList.size());
        return movieList.size();
    }

    // Add this method to update the movies in the adapter
    public void updateMovies(List<MovieResponse.Movie> movies) {
        Log.d(TAG, "Updating movies in adapter, new size: " + movies.size());
        this.movieList = movies;
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            posterImageView = itemView.findViewById(R.id.image_view_poster);
        }
    }
}