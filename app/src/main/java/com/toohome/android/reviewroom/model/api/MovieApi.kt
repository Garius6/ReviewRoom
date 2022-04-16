package com.toohome.android.reviewroom.model.api

import com.toohome.android.reviewroom.model.Comment
import com.toohome.android.reviewroom.model.Movie
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") movieId: Long): Response<Movie>

    @GET("movies")
    suspend fun getMovies(): Response<List<Movie>>

    @POST("movie/{id}/comment")
    suspend fun createCommentForMovie(@Path("id") movieId: Long, @Body comment: Comment)

    @GET("movie/{id}/comments")
    suspend fun getComments(@Path("id") movieId: Long): Response<List<Comment>>
}
