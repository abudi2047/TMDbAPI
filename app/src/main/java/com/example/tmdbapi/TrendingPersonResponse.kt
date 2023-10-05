package com.example.tmdbapi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class TrendingPersonResponse(
    val results: List<Person>
)

@Parcelize
data class Person(
    val profile_path: String?,
    val name: String,
    val popularity: Double,
    val known_for_department: String  // Ensure this field actually exists in the API response or adjust accordingly.
) : Parcelable
