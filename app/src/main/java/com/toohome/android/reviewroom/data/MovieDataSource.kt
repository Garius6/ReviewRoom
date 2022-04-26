package com.toohome.android.reviewroom.data

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.Movie
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDataSource(private val baseUrl: HttpUrl) {
    private lateinit var movieService: MovieService

    fun setClient(client: OkHttpClient) {
        movieService = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(MovieService::class.java)
    }

    suspend fun getMovies(): Response<List<Movie>> = movieService.getMovies()

    suspend fun getMovie(id: Long): Response<Movie> = movieService.getMovie(id)

    suspend fun createCommentForMovie(movieId: Long, comment: Comment) =
        movieService.createCommentForMovie(movieId, comment)

    suspend fun getComments(movieId: Long): Response<List<Comment>> =
        movieService.getComments(movieId)

    fun loadPosterIntoViewWithPlaceholder(
        moviePosterUrl: String,
        into: ImageView,
        @DrawableRes placeholder: Int = R.drawable.ic_poster_placeholder,
    ) {
        val url = "$baseUrl$moviePosterUrl"
        Picasso.get().load(url).placeholder(placeholder).into(into)
    }
}
