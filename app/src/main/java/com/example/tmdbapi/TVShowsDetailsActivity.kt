package com.example.tmdbapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

// Updated constant names to avoid conflicts
const val TV_SHOWS_BACKDROP = "extra_tvshow_backdrop"
const val TV_SHOWS_POSTER = "extra_tvshow_poster"
const val TV_SHOWS_TITLE = "extra_tvshow_title"
const val TV_SHOWS_RATING = "extra_tvshow_rating"
const val TV_SHOWS_RELEASE_DATE = "extra_tvshow_release_date"
const val TV_SHOWS_OVERVIEW = "extra_tvshow_overview"

class TVShowsDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details) // Ensure this is the correct layout file for TV show details

        // Initialize UI elements
        backdrop = findViewById(R.id.movie_backdrop) // Update the ID to match the TV show details layout
        poster = findViewById(R.id.movie_poster) // Update the ID to match the TV show details layout
        title = findViewById(R.id.movie_title) // Update the ID to match the TV show details layout
        rating = findViewById(R.id.movie_rating) // Update the ID to match the TV show details layout
        releaseDate = findViewById(R.id.movie_release_date) // Update the ID to match the TV show details layout
        overview = findViewById(R.id.movie_overview) // Update the ID to match the TV show details layout

        val extras = intent.extras
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(TV_SHOWS_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w780$backdropPath") // Changed to w780 for better resolution on most devices
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(TV_SHOWS_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(TV_SHOWS_TITLE, "")
        rating.rating = extras.getFloat(TV_SHOWS_RATING, 0f) / 2
        releaseDate.text = extras.getString(TV_SHOWS_RELEASE_DATE, "")
        overview.text = extras.getString(TV_SHOWS_OVERVIEW, "")
    }
}
