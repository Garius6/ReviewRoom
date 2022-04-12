package com.toohome.android.reviewroom.model.api

import com.toohome.android.reviewroom.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") movieId: Long): Response<Movie>

    @GET("movies")
    suspend fun getMovies(): Response<List<Movie>>
}
