package com.toohome.android.reviewroom.data.model

data class MovieCollection(
    val id: Long = 0,
    val name: String,
    val authorId: Long,
    val movies: List<Movie>
)
