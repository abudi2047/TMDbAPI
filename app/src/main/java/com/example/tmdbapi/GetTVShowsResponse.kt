package com.example.tmdbapi

import com.google.gson.annotations.SerializedName

data class GetTVShowsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val tvshows: List<TVShows>,
    @SerializedName("total_pages") val pages: Int
)
