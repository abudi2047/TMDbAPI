package com.example.tmdbapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "5575de1681ca7d8c20c4d05f6ffe9ffc",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "5575de1681ca7d8c20c4d05f6ffe9ffc",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "5575de1681ca7d8c20c4d05f6ffe9ffc",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("tv/popular")
    fun getPopularTVShows(
        @Query("api_key") apiKey: String = "5575de1681ca7d8c20c4d05f6ffe9ffc",
        @Query("page") page: Int
    ): Call<GetTVShowsResponse>

    @GET("tv/top_rated")
    fun getTopRatedTVShows(
        @Query("api_key") apiKey: String = "5575de1681ca7d8c20c4d05f6ffe9ffc",
        @Query("page") page: Int
    ): Call<GetTVShowsResponse>

    @GET("tv/airing_today")
    fun getOnAirTVShows(
        @Query("api_key") apiKey: String = "5575de1681ca7d8c20c4d05f6ffe9ffc",
        @Query("page") page: Int
    ): Call<GetTVShowsResponse>

    @GET("trending/people/week")
    fun getTrendingPeople(): Call<TrendingPersonResponse>

}
