package com.toohome.android.reviewroom.data

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.Movie
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDataSource(private val baseUrl: HttpUrl) {
    private lateinit var movieApi: MovieApi

    fun setToken(token: String) {
        val client = OkHttpClient.Builder().addInterceptor {
            val originalRequest: Request = it.request()
            if (originalRequest.header("Authorization") != null) {
                it.proceed(originalRequest)
            }

            val compressedRequest: Request = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .method(originalRequest.method(), originalRequest.body())
                .build()
            it.proceed(compressedRequest)
        }.build()

        movieApi = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(MovieApi::class.java)
    }

    suspend fun getMovies(): Response<List<Movie>> = movieApi.getMovies()

    suspend fun getMovie(id: Long): Response<Movie> = movieApi.getMovie(id)

    suspend fun createCommentForMovie(movieId: Long, comment: Comment) =
        movieApi.createCommentForMovie(movieId, comment)

    suspend fun getComments(movieId: Long): Response<List<Comment>> = movieApi.getComments(movieId)

    fun loadPosterIntoViewWithPlaceholder(
        moviePosterUrl: String,
        into: ImageView,
        @DrawableRes placeholder: Int = R.drawable.ic_poster_placeholder,
    ) {
        val url = "$baseUrl$moviePosterUrl"
        Picasso.get().load(url).placeholder(placeholder).into(into)
    }
}
