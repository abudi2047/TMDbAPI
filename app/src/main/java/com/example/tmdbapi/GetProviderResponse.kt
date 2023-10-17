package com.example.tmdbapi

import com.google.gson.annotations.SerializedName

data class GetMovieProvidersResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val providers: List<MovieProvider>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
