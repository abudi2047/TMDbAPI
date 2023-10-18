package com.example.tmdbapi.repository

import android.util.Log
import com.example.tmdbapi.Api
import com.example.tmdbapi.Person
import com.example.tmdbapi.TrendingPersonResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

object PeopleRepository {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "5575de1681ca7d8c20c4d05f6ffe9ffc"
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getTrendingPeople(
        page: Int = 1,
        onSuccess: (List<Person>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getTrendingPeople(API_KEY, page).enqueue(object : Callback<TrendingPersonResponse> {
            override fun onResponse(
                call: Call<TrendingPersonResponse>,
                response: Response<TrendingPersonResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.results?.let(onSuccess) ?: onError("Response body is null")
                } else {
                    // Log error details for debugging.
                    Log.e("PeopleRepository", "Response error: ${response.errorBody()?.string()}")
                    onError("Response error ${response.code()}: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<TrendingPersonResponse>, t: Throwable) {
                // Log failure message for debugging.
                Log.e("PeopleRepository", "API call failed", t)
                onError("API call failed: ${t.localizedMessage}")
            }
        })
    }
}
