package com.toohome.android.reviewroom.model.api

import com.toohome.android.reviewroom.model.Comment
import com.toohome.android.reviewroom.model.Movie
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiFetcher(baseUrl: HttpUrl) {
    private val movieApi: MovieApi = Retrofit.Builder()
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
        .create(MovieApi::class.java)

    suspend fun getMovies(): Response<List<Movie>> = movieApi.getMovies()

    suspend fun getMovie(id: Long): Response<Movie> = movieApi.getMovie(id)

    suspend fun createCommentForMovie(movieId: Long, comment: Comment) =
        movieApi.createCommentForMovie(movieId, comment)

    suspend fun getComments(movieId: Long): Response<List<Comment>> = movieApi.getComments(movieId)
}
