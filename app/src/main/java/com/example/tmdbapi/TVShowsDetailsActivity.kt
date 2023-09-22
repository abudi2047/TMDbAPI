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
        setContentView(R.layout.activity_tvshows_details) // Corrected layout file name

        // Initialize UI elements
        backdrop = findViewById(R.id.tvshows_backdrop) // Corrected ID
        poster = findViewById(R.id.tvshows_poster) // Corrected ID
        title = findViewById(R.id.tvshows_title) // Corrected ID
        rating = findViewById(R.id.tvshows_rating) // Corrected ID
        releaseDate = findViewById(R.id.tvshows_release_date) // Corrected ID
        overview = findViewById(R.id.tvshows_overview) // Corrected ID

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
                .load("https://image.tmdb.org/t/p/w780$backdropPath")
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
