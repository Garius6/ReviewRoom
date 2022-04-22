package com.toohome.android.reviewroom.data

import android.widget.ImageView
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.LoggedUser
import com.toohome.android.reviewroom.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserMovieRepository(
    private val movieDataSource: MovieDataSource,
    private val loginDataSource: LoginDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    var user: LoggedUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    private fun setLoggedInUser(loggedInUser: LoggedUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    suspend fun login(username: String, password: String): Result<LoggedUser, Exception> {
        return withContext(defaultDispatcher) {
            // handle login
            val result = loginDataSource.login(username, password)

            if (result is SuccessResult) {
                setLoggedInUser(result.data)
            }

            result
        }
    }

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
        return withContext(defaultDispatcher) { invokeCall { movieDataSource.getMovies() } }
    }

    suspend fun getMovie(id: Long): Result<Movie, Exception> {
        return withContext(defaultDispatcher) { invokeCall { movieDataSource.getMovie(id) } }
    }

    suspend fun newCommentForMovie(movieId: Long, comment: Comment): Result<Boolean, Exception> {
        return withContext(defaultDispatcher) {
            try {
                movieDataSource.createCommentForMovie(movieId, comment)
                SuccessResult(true)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    suspend fun getComments(movieId: Long): Result<List<Comment>, Exception> {
        return withContext(defaultDispatcher) {
            invokeCall {
                movieDataSource.getComments(
                    movieId
                )
            }
        }
    }

    fun loadPostIntoImageView(movie: Movie, into: ImageView) {
        val url = movie.posterUrl
        movieDataSource.loadPosterIntoViewWithPlaceholder(url, into = into)
    }

    companion object {
        private var INSTANCE: UserMovieRepository? = null

        fun initialize(movieDataSource: MovieDataSource, loginDataSource: LoginDataSource) {
            if (INSTANCE == null) {
                INSTANCE = UserMovieRepository(
                    movieDataSource = movieDataSource,
                    loginDataSource = loginDataSource
                )
            }
        }

        fun get(): UserMovieRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}
