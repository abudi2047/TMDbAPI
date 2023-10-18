package com.example.tmdbapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieProvider(
    @SerializedName("provider_Id") val id: Int,
    @SerializedName("provider_Name") val name: String,
    @SerializedName ("logo_Path") val logoPath: String,
    @SerializedName ("display_Priority") val displayPriority: Int
) : Parcelable
