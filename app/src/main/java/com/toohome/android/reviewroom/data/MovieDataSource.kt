package com.toohome.android.reviewroom.data

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.Movie
import okhttp3.HttpUrl
import retrofit2.Response
import retrofit2.Retrofit

class MovieDataSource() {
    private lateinit var movieService: MovieService
    private lateinit var baseUrl: HttpUrl

    fun setRetrofit(retrofit: Retrofit) {
        movieService =
            retrofit.create(MovieService::class.java)
        baseUrl = retrofit.baseUrl()
    }

    suspend fun getMovies(): Response<List<Movie>> = movieService.getMovies()

    suspend fun getMovie(id: Long): Response<Movie> = movieService.getMovie(id)

    suspend fun createCommentForMovie(movieId: Long, comment: Comment): Response<Void> =
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
