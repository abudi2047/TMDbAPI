package com.example.tmdbapi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onMovieClick)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        val positionStart = this.movies.size
        this.movies.addAll(movies)
        notifyItemRangeInserted(positionStart, movies.size)
    }

    class MovieViewHolder(itemView: View, private val onMovieClick: (movie: Movie) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            val imageUrl = "https://image.tmdb.org/t/p/w342${movie.posterPath}"
            Log.d("MoviesAdapter", "Loading image from: $imageUrl")

            Glide.with(itemView)
                .load(imageUrl)
                .transform(CenterCrop())
                .into(poster)

            itemView.setOnClickListener { onMovieClick(movie) }
        }
    }
}
