package com.toohome.android.reviewroom.data.model

data class MovieCollection(
    val id: Long,
    val authorId: Long,
    val movies: List<Movie>
)
