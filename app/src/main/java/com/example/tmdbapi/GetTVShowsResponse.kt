package com.example.tmdbapi

data class GetTVShowsResponse(
    val results: List<TVShow>
)

data class TVShow(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val backdropPath: String,
    val posterPath: String,
    // ... other attributes
)

