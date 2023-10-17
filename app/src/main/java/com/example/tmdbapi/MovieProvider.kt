package com.example.tmdbapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieProvider(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName ("logoPath") val logoPath: String,
    @SerializedName ("display_Priority") val displayPriority: Int
) : Parcelable
