package com.toohome.android.reviewroom.data

import android.widget.ImageView
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.Movie
import com.toohome.android.reviewroom.utils.ErrorResult
import com.toohome.android.reviewroom.utils.Result
import com.toohome.android.reviewroom.utils.SuccessResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
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
        return withContext(defaultDispatcher) { invokeCall { movieRemoteDataSource.getMovies() } }
    }

    suspend fun getMovie(id: Long): Result<Movie, Exception> {
        return withContext(defaultDispatcher) { invokeCall { movieRemoteDataSource.getMovie(id) } }
    }

    suspend fun newCommentForMovie(movieId: Long, comment: Comment): Result<Boolean, Exception> {
        return withContext(defaultDispatcher) {
            try {
                movieRemoteDataSource.createCommentForMovie(movieId, comment)
                SuccessResult(true)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    suspend fun getComments(movieId: Long): Result<List<Comment>, Exception> {
        return withContext(defaultDispatcher) {
            invokeCall {
                movieRemoteDataSource.getComments(
                    movieId
                )
            }
        }
    }

    fun loadPostIntoImageView(movie: Movie, into: ImageView) {
        val url = movie.posterUrl
        movieRemoteDataSource.loadPosterIntoViewWithPlaceholder(url, into = into)
    }

    companion object {
        private var INSTANCE: MovieRepository? = null

        fun initialize(movieRemoteDataSource: MovieRemoteDataSource) {
            if (INSTANCE == null) {
                INSTANCE = MovieRepository(movieRemoteDataSource = movieRemoteDataSource)
            }
        }

        fun get(): MovieRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}
