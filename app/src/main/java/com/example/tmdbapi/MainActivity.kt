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
    private lateinit var onTheAirTVShowsAdapter: TVShowsAdapter
    private lateinit var topRatedTVShowsAdapter: TVShowsAdapter

    // Add the following page variables
    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    private var upcomingMoviesPage = 1
    private var popularTVShowsPage = 1
    private var onTheAirTVShowsPage = 1
    private var topRatedTVShowsPage = 1

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
            { showMovieDetails(it as Movie) },
            { popularMoviesAdapter = it as MoviesAdapter }
        )

        setupRecyclerView(
            R.id.top_rated_movies,
            ::fetchTopRatedMovies,
            { showMovieDetails(it as Movie) },
            { topRatedMoviesAdapter = it as MoviesAdapter }
        )

        setupRecyclerView(
            R.id.upcoming_movies,
            ::fetchUpcomingMovies,
            { showMovieDetails(it as Movie) },
            { upcomingMoviesAdapter = it as MoviesAdapter }
        )

        setupRecyclerView(
            R.id.popular_tvshows,
            ::fetchPopularTVShows,
            { showTVShowDetails(it as TVShows) },
            { popularTVShowsAdapter = it as TVShowsAdapter }
        )

        setupRecyclerView(
            R.id.on_the_air_tvshows,
            ::fetchOnTheAirTVShows,
            { showTVShowDetails(it as TVShows) },
            { onTheAirTVShowsAdapter = it as TVShowsAdapter }
        )

        setupRecyclerView(
            R.id.top_rated_tvshows,
            ::fetchTopRatedTVShows,
            { showTVShowDetails(it as TVShows) },
            { topRatedTVShowsAdapter = it as TVShowsAdapter }
        )

    }
    private fun setupRecyclerView(
        recyclerViewId: Int,
        fetchFunction: (Int) -> Unit,
        itemClickAction: (Any) -> Unit,
        adapterSetter: (RecyclerView.Adapter<*>) -> Unit
    ) {
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = when (recyclerViewId) {
            R.id.popular_tvshows, R.id.on_the_air_tvshows, R.id.top_rated_tvshows -> TVShowsAdapter(mutableListOf(), itemClickAction as (Any) -> Unit)
            else -> MoviesAdapter(mutableListOf(), itemClickAction)
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
        setupScrollListener(R.id.on_the_air_tvshows, ::fetchOnTheAirTVShows)
        setupScrollListener(R.id.top_rated_tvshows, ::fetchTopRatedTVShows)
    }

    private fun setupScrollListener(recyclerViewId: Int, fetchFunction: (Int) -> Unit) {
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        recyclerView.setEndlessScrollListener {
            val nextPage = when (recyclerViewId) {
                R.id.popular_movies -> ++popularMoviesPage
                R.id.top_rated_movies -> ++topRatedMoviesPage
                R.id.upcoming_movies -> ++upcomingMoviesPage
                R.id.popular_tvshows -> ++popularTVShowsPage
                R.id.on_the_air_tvshows -> ++onTheAirTVShowsPage
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

    private fun fetchOnTheAirTVShows(page: Int) {
        MediaRepository.getOnAirTVShows(page, ::onOnTheAirTVShowsFetched, ::onError)
    }

    private fun fetchTopRatedTVShows(page: Int) {
        MediaRepository.getTopRatedTVShows(
            onSuccess = { tvShows ->
                topRatedTVShowsAdapter.appendTVShows(tvShows)
            },
            onError = {
                onError()
            }
        )
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

    private fun onOnTheAirTVShowsFetched(tvShows: List<TVShows>) {
        onTheAirTVShowsAdapter.appendTVShows(tvShows)
    }

    private fun onError() {
        Toast.makeText(this, "Failed to get content", Toast.LENGTH_SHORT).show()
    }

    private fun showItemDetails(item: Any) {
        if (item is Movie) {
            showMovieDetails(item)
        } else if (item is TVShows) {
            showTVShowDetails(item)
        }
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

    private fun showTVShowDetails(tvShow: TVShows) {
        val intent = Intent(this, TVShowsDetailsActivity::class.java)
        intent.putExtra(TV_SHOWS_BACKDROP, tvShow.backdropPath)
        intent.putExtra(TV_SHOWS_POSTER, tvShow.posterPath)
        intent.putExtra(TV_SHOWS_TITLE, tvShow.title)
        intent.putExtra(TV_SHOWS_RATING, tvShow.rating)
        intent.putExtra(TV_SHOWS_RELEASE_DATE, tvShow.firstAirDate)
        intent.putExtra(TV_SHOWS_OVERVIEW, tvShow.overview)
        startActivity(intent)
    }


}
