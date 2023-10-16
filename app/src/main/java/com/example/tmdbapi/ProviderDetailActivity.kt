package com.example.tmdbapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ProviderDetailActivity : AppCompatActivity() {

    private lateinit var adapter: MovieProvidersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_detail)

        // Initialize the adapter and RecyclerView
        adapter = MovieProvidersAdapter(emptyList()) { movieProvider ->
            // Handle the click on a movie provider. Maybe show more details or navigate to another screen?
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Assuming you have a RecyclerView with this ID in activity_provider_detail.xml
        recyclerView.adapter = adapter

        MediaRepository.getMovieProviders(
            onSuccess = { movieProviders ->
                if (movieProviders.all { it is MovieProvider }) {
                    adapter.updateData(movieProviders as List<MovieProvider>)
                } else {
                    // Handle the type mismatch or try to cast/convert the data to the expected type
                }
            },
            onError = {
                // Handle error, maybe show a Toast or a Snackbar
            }
        )
    }
}
