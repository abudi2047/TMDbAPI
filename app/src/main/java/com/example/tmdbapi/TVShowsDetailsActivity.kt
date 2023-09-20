package com.example.tmdbapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TVShowsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshows_details)
    }

    private fun showTVShowDetails(tvShow: TVShow) {
        val intent = Intent(this, TVShowsDetailsActivity::class.java)
        // Pass TV show data to the details activity if needed
        // For example: intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }

}