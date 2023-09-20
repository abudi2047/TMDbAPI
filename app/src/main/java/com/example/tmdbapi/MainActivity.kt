package com.example.tmdbapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var popularTVShowsAdapter: TVShowsAdapter

    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    private var upcomingMoviesPage = 1
    private var popularTVShowsPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUIComponents()
        setScrollListeners()
    }

    private fun initUIComponents() {
        setupRecyclerView(
            R.id.popular_movies,
            ::fetchPopularMovies,
            { showMovieDetails(it) }
        ) { popularMoviesAdapter = it as MoviesAdapter }

        setupRecyclerView(
            R.id.top_rated_movies,
            ::fetchTopRatedMovies,
            { showMovieDetails(it) }
        ) { topRatedMoviesAdapter = it as MoviesAdapter }

        setupRecyclerView(
            R.id.upcoming_movies,
            ::fetchUpcomingMovies,
            { showMovieDetails(it) }
        ) { upcomingMoviesAdapter = it as MoviesAdapter }

        setupRecyclerView(
            R.id.popular_tvshows,
            ::fetchPopularTVShows,
            { showTVShowDetails(it) } // Call the showTVShowDetails function for TV shows
        ) { popularTVShowsAdapter = it as TVShowsAdapter }
    }

    private fun setupRecyclerView(
        recyclerViewId: Int,
        fetchFunction: (Int) -> Unit,
        clickAction: (Movie) -> Unit,
        adapterSetter: (RecyclerView.Adapter<*>) -> Unit
    ) {
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = when (recyclerViewId) {
            R.id.popular_tvshows -> TVShowsAdapter() // Use TVShowsAdapter for TV shows
            else -> MoviesAdapter(mutableListOf(), clickAction)
        }

        adapterSetter(adapter)
        recyclerView.adapter = adapter
        fetchFunction(1)
    }

    private fun setScrollListeners() {
        setupScrollListener(R.id.popular_movies, ::fetchPopularMovies)
        setupScrollListener(R.id.top_rated_movies, ::fetchTopRatedMovies)
        setupScrollListener(R.id.upcoming_movies, ::fetchUpcomingMovies)
        setupScrollListener(R.id.popular_tvshows, ::fetchPopularTVShows)
    }

    private fun setupScrollListener(recyclerViewId: Int, fetchFunction: (Int) -> Unit) {
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        recyclerView.setEndlessScrollListener {
            val nextPage = when (recyclerViewId) {
                R.id.popular_movies -> ++popularMoviesPage
                R.id.top_rated_movies -> ++topRatedMoviesPage
                R.id.upcoming_movies -> ++upcomingMoviesPage
                R.id.popular_tvshows -> ++popularTVShowsPage
                else -> 1
            }
            fetchFunction(nextPage)
        }
    }

    private fun RecyclerView.setEndlessScrollListener(action: () -> Unit) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() + 1 >= layoutManager.itemCount) {
                    action()
                }
            }
        })
    }

    private fun fetchPopularMovies(page: Int) {
        MediaRepository.getPopularMovies(page, ::onPopularMoviesFetched, ::onError)
    }

    private fun fetchTopRatedMovies(page: Int) {
        MediaRepository.getTopRatedMovies(page, ::onTopRatedMoviesFetched, ::onError)
    }

    private fun fetchUpcomingMovies(page: Int) {
        MediaRepository.getUpcomingMovies(page, ::onUpcomingMoviesFetched, ::onError)
    }

    private fun fetchPopularTVShows(page: Int) {
        MediaRepository.getPopularTVShows(page, ::onPopularTVShowsFetched, ::onError)
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
    }

    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        upcomingMoviesAdapter.appendMovies(movies)
    }

    private fun onPopularTVShowsFetched(tvShows: List<TVShows>) {
        popularTVShowsAdapter.appendTVShows(tvShows)
    }

    private fun onError() {
        Toast.makeText(this, "Failed to get content", Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(intent)
    }

    private fun showTVShowDetails(tvShow: Movie) {
        val intent = Intent(this, TVShowsDetailsActivity::class.java)
        // Pass TV show data to the details activity if needed
        // For example: intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }
}
