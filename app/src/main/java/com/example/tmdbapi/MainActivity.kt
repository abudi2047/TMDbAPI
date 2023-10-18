package com.example.tmdbapi

//import ProviderDetailActivity
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbapi.MovieDetailsActivity.Companion.MOVIE_BACKDROP
import com.example.tmdbapi.MovieDetailsActivity.Companion.MOVIE_OVERVIEW
import com.example.tmdbapi.MovieDetailsActivity.Companion.MOVIE_POSTER
import com.example.tmdbapi.MovieDetailsActivity.Companion.MOVIE_RATING
import com.example.tmdbapi.MovieDetailsActivity.Companion.MOVIE_RELEASE_DATE
import com.example.tmdbapi.MovieDetailsActivity.Companion.MOVIE_TITLE
import com.example.tmdbapi.TVShowsDetailsActivity.Companion.TV_SHOWS_BACKDROP
import com.example.tmdbapi.TVShowsDetailsActivity.Companion.TV_SHOWS_OVERVIEW
import com.example.tmdbapi.TVShowsDetailsActivity.Companion.TV_SHOWS_POSTER
import com.example.tmdbapi.TVShowsDetailsActivity.Companion.TV_SHOWS_RATING
import com.example.tmdbapi.TVShowsDetailsActivity.Companion.TV_SHOWS_RELEASE_DATE
import com.example.tmdbapi.TVShowsDetailsActivity.Companion.TV_SHOWS_TITLE
import com.example.tmdbapi.repository.PeopleRepository
import com.google.android.material.color.utilities.MaterialDynamicColors
//import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var popularTVShowsAdapter: TVShowsAdapter
    private lateinit var onTheAirTVShowsAdapter: TVShowsAdapter
    private lateinit var topRatedTVShowsAdapter: TVShowsAdapter
    private lateinit var trendingPersonAdapter: TrendingPersonAdapter
    private lateinit var movieProvidersAdapter: MovieProvidersAdapter

    private val trendingPeopleRecyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.trendingPeopleRecyclerView) }

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

        val rvMovieProviders: RecyclerView = findViewById(R.id.rv_movie_providers)
        rvMovieProviders.layoutManager = LinearLayoutManager(this)

        movieProvidersAdapter = MovieProvidersAdapter(ArrayList()) { movieProvider ->
            // Handle the provider item click here
            val intent = Intent(this, ProviderDetailActivity::class.java)
            intent.putExtra("extra_provider", movieProvider)
            startActivity(intent)
        }
        rvMovieProviders.adapter = movieProvidersAdapter

        MediaRepository.getMovieProviders({ movieProviders ->
            movieProvidersAdapter.updateData(movieProviders)
        }, ::onError)
    }


    class HorizontalSpacingItemDecorator(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
                outRect.right = space
            }
        }
    }

    private fun initUIComponents() {
        trendingPeopleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        trendingPersonAdapter = TrendingPersonAdapter(mutableListOf())
        trendingPeopleRecyclerView.adapter = trendingPersonAdapter

        trendingPeopleRecyclerView.addItemDecoration(HorizontalSpacingItemDecorator(6))

        trendingPersonAdapter.onItemClick = { selectedPerson ->
            val intent = Intent(this, TrendingPersonDetailActivity::class.java)
            intent.putExtra("selected_person", selectedPerson)
            startActivity(intent)
        }

        fetchTrendingPeople()

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
            R.id.popular_tvshows, R.id.on_the_air_tvshows, R.id.top_rated_tvshows -> TVShowsAdapter(
                mutableListOf(),
                itemClickAction as (TVShows) -> Unit
            )
            else -> MoviesAdapter(mutableListOf(), itemClickAction as (Movie) -> Unit)
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
                R.id.top_rated_tvshows -> ++topRatedTVShowsPage
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
        MediaRepository.getTopRatedTVShows(page, ::onTopRatedTVShowsFetched, ::onError)
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

    private fun onTopRatedTVShowsFetched(tvShows: List<TVShows>) {
        topRatedTVShowsAdapter.appendTVShows(tvShows)
    }

    private fun fetchTrendingPeople() {
        val page = 1
        PeopleRepository.getTrendingPeople(
            page,
            ::onTrendingPeopleFetched,
            ::onError
        )
    }

    private fun onTrendingPeopleFetched(people: List<Person>) {
        trendingPersonAdapter.appendPeople(people)
    }

    private fun onError(errorMessage: String = "An error occurred") {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val TV_SHOW_ID = "TV_SHOW_ID"
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
