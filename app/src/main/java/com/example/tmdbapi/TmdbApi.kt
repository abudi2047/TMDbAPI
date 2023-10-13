package com.example.tmdbapi

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("/watch/providers/movie")
    fun getMovieProviders(): Call<List<MovieProvider>>

    @GET("trending/person/week")
    fun getTrendingPeople(@Query("api_key") apiKey: String): Call<TrendingPersonResponse>

}
