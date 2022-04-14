package com.toohome.android.reviewroom.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Long,
    val name: String,
    @SerializedName("poster_url") val posterUrl: String
)
