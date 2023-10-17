package com.example.tmdbapi

import android.util.Log
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MediaRepository {

    private const val API_KEY = "5575de1681ca7d8c20c4d05f6ffe9ffc"

    private val api: Api

    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(API_KEY, page)
            .enqueue(createMoviesCallback(onSuccess, onError))
    }

    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedMovies(API_KEY, page)
            .enqueue(createMoviesCallback(onSuccess, onError))
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getUpcomingMovies(API_KEY, page)
            .enqueue(createMoviesCallback(onSuccess, onError))
    }

    fun getPopularTVShows(
        page: Int = 1,
        onSuccess: (tvShows: List<TVShows>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularTVShows(API_KEY, page)
            .enqueue(createTVShowsCallback(onSuccess, onError))
    }

    fun getOnAirTVShows(
        page: Int = 1,
        onSuccess: (tvShows: List<TVShows>) -> Unit,
        onError: () -> Unit
    ) {
        api.getOnAirTVShows(API_KEY, page)
            .enqueue(createTVShowsCallback(onSuccess, onError))
    }

    fun getTopRatedTVShows(
        page: Int = 1,
        onSuccess: (tvShows: List<TVShows>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedTVShows(API_KEY, page)
            .enqueue(createTVShowsCallback(onSuccess, onError))
    }

    fun getMovieProviders(onSuccess: (movieProviders: List<MovieProvider>) -> Unit, onError: (error: String) -> Unit) {
        Log.d("MediaRepository", "Attempting to fetch movie providers.")
        api.getMovieProviders(API_KEY, 1).enqueue(object : Callback<GetMovieProvidersResponse> {
            override fun onResponse(call: Call<GetMovieProvidersResponse>, response: Response<GetMovieProvidersResponse>) {
                if (response.isSuccessful) {
                    val movieProviders = response.body()?.providers
                    if (movieProviders != null) {
                        onSuccess(movieProviders)
                    } else {
                        onError("Response body is null")
                    }
                } else {
                    onError("Response error ${response.code()}: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GetMovieProvidersResponse>, t: Throwable) {
                onError("API call failed: ${t.localizedMessage}")
            }
        })
    }

    private fun createMoviesCallback(
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ): Callback<GetMoviesResponse> {
        return object : Callback<GetMoviesResponse> {
            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        onSuccess.invoke(responseBody.movies)
                    } else {
                        onError.invoke()
                        Log.e("MediaRepository", "Movies response body is null")
                    }
                } else {
                    onError.invoke()
                    Log.e("MediaRepository", "Movies response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                onError.invoke()
                Log.e("MediaRepository", "Movies API call failed: ${t.message}")
            }
        }
    }

    private fun createTVShowsCallback(
        onSuccess: (tvShows: List<TVShows>) -> Unit,
        onError: () -> Unit
    ): Callback<GetTVShowsResponse> {
        return object : Callback<GetTVShowsResponse> {
            override fun onResponse(
                call: Call<GetTVShowsResponse>,
                response: Response<GetTVShowsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        onSuccess.invoke(responseBody.tvshows)
                    } else {
                        onError.invoke()
                        Log.e("MediaRepository", "TVShows response body is null")
                    }
                } else {
                    onError.invoke()
                    Log.e("MediaRepository", "TVShows response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GetTVShowsResponse>, t: Throwable) {
                onError.invoke()
                Log.e("MediaRepository", "TVShows API call failed: ${t.message}")
            }
        }
    }
}
