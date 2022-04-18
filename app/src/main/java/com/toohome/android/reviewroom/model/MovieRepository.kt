package com.toohome.android.reviewroom.model

import android.widget.ImageView
import com.toohome.android.reviewroom.model.api.MovieApiFetcher
import com.toohome.android.reviewroom.utils.ErrorResult
import com.toohome.android.reviewroom.utils.Result
import com.toohome.android.reviewroom.utils.SuccessResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepository(
    private val movieApiFetcher: MovieApiFetcher,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private suspend fun <T> invokeCall(call: suspend () -> Response<T>): Result<T, Exception> {
        return try {
            val res = call.invoke().body()
                ?: throw IllegalStateException("Response object cannot be null")
            SuccessResult(res)
        } catch (e: Exception) {
            ErrorResult(e)
        }
    }

    suspend fun getMovies(): Result<List<Movie>, Exception> {
        return withContext(defaultDispatcher) { invokeCall { movieApiFetcher.getMovies() } }
    }

    suspend fun getMovie(id: Long): Result<Movie, Exception> {
        return withContext(defaultDispatcher) { invokeCall { movieApiFetcher.getMovie(id) } }
    }

    suspend fun newCommentForMovie(movieId: Long, comment: Comment): Result<Boolean, Exception> {
        return withContext(defaultDispatcher) {
            try {
                movieApiFetcher.createCommentForMovie(movieId, comment)
                SuccessResult(true)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    suspend fun getComments(movieId: Long): Result<List<Comment>, Exception> {
        return withContext(defaultDispatcher) { invokeCall { movieApiFetcher.getComments(movieId) } }
    }

    fun loadPostIntoImageView(movie: Movie, into: ImageView) {
        val url = movie.posterUrl
        movieApiFetcher.loadPosterIntoViewWithPlaceholder(url, into = into)
    }

    companion object {
        private var INSTANCE: MovieRepository? = null

        fun initialize(movieApiFetcher: MovieApiFetcher) {
            if (INSTANCE == null) {
                INSTANCE = MovieRepository(movieApiFetcher = movieApiFetcher)
            }
        }

        fun get(): MovieRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}
