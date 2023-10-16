package com.example.tmdbapi

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieProvider(
    val id: Int,
    val name: String,
    val logoPath: String,
    val displayPriority: Int
) : Parcelable
