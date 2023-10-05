package com.example.tmdbapi.repository

import com.example.tmdbapi.Person
import com.example.tmdbapi.TmdbApi
import com.example.tmdbapi.TrendingPersonResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

object PeopleRepository {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "5575de1681ca7d8c20c4d05f6ffe9ffc"

    private val api: TmdbApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TmdbApi::class.java)
    }

    fun getTrendingPeople(
        onSuccess: (List<Person>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTrendingPeople(API_KEY).enqueue(object : Callback<TrendingPersonResponse> {
            override fun onResponse(
                call: Call<TrendingPersonResponse>,
                response: Response<TrendingPersonResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        onSuccess.invoke(responseBody.results)
                    } else {
                        onError.invoke()
                    }
                } else {
                    onError.invoke()
                }
            }

            override fun onFailure(call: Call<TrendingPersonResponse>, t: Throwable) {
                onError.invoke()
            }
        })
    }
}
