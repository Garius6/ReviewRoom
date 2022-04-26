package com.toohome.android.reviewroom.data

import android.util.Log
import android.widget.ImageView
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.LoggedUser
import com.toohome.android.reviewroom.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

private const val TAG = "UserMovieRepository"

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
    }

    suspend fun login(username: String, password: String): Result<LoggedUser, Exception> {
        // handle login
        val result = loginDataSource.login(username, password)
        Log.d(TAG, result.toString())
        if (result is SuccessResult) {
            movieDataSource.setClient(
                OkHttpClient.Builder().addInterceptor {
                    val originalRequest: Request = it.request()
                    if (originalRequest.header("Authorization") != null) {
                        it.proceed(originalRequest)
                    }

                    val authorizedRequest: Request = originalRequest.newBuilder()
                        .header("Authorization", "Bearer ${loginDataSource.tokenPair.accessToken}")
                        .method(originalRequest.method(), originalRequest.body())
                        .build()
                    it.proceed(authorizedRequest)
                }.authenticator(loginDataSource.getTokenAuthenticator()).build()
            )
            setLoggedInUser(result.data)
        }

        return result
    }

    private suspend fun <T> invokeCall(call: suspend () -> Response<T>): Result<T, Exception> {
        return try {
            val res = call.invoke().body()
                ?: throw IllegalStateException("Response object cannot be null")
            SuccessResult(res)
        } catch (e: Exception) {
            Log.d(TAG, e.stackTraceToString())
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
