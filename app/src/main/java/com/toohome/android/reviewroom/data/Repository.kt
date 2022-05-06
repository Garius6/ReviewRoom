package com.toohome.android.reviewroom.data

import android.util.Log
import android.widget.ImageView
import com.toohome.android.reviewroom.data.model.Comment
import com.toohome.android.reviewroom.data.model.LoggedUser
import com.toohome.android.reviewroom.data.model.Movie
import com.toohome.android.reviewroom.data.model.MovieCollection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "UserMovieRepository"

class Repository(
    private val baseUrl: HttpUrl,
    private val movieDataSource: MovieDataSource,
    private val loginDataSource: LoginDataSource,
    private val collectionDataSource: CollectionDataSource,
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
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(loginDataSource.getLoginClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            movieDataSource.setRetrofit(retrofit)
            collectionDataSource.setRetrofit(retrofit)
            setLoggedInUser(result.data)
        }

        return result
    }

    private suspend fun <T> invokeCallWithBody(call: suspend () -> Response<T>): Result<T, Exception> {
        return try {
            val res = call.invoke().body()
                ?: throw IllegalStateException("Response object cannot be null")
            SuccessResult(res)
        } catch (e: Exception) {
            Log.d(TAG, e.stackTraceToString())
            ErrorResult(e)
        }
    }

    private suspend fun invokeCall(call: suspend () -> Response<Void>): Result<Boolean, Exception> {
        return try {
            if (call.invoke().code() != 200) {
                return ErrorResult(IllegalArgumentException("Response code is not OK"))
            }
            SuccessResult(true)
        } catch (e: Exception) {
            Log.d(TAG, e.stackTraceToString())
            ErrorResult(e)
        }
    }

    suspend fun getCollections(filter: Filter): Result<List<MovieCollection>, Exception> {
        return withContext(defaultDispatcher) {
            invokeCallWithBody {
                collectionDataSource.getCollections(
                    filter
                )
            }
        }
    }

    suspend fun getCollection(id: Long): Result<MovieCollection, Exception> {
        return withContext(defaultDispatcher) { invokeCallWithBody { collectionDataSource.getCollection(id) } }
    }

    suspend fun createCollection(collection: MovieCollection): Result<Boolean, Exception> {
        return withContext(defaultDispatcher) {
            invokeCall {
                collectionDataSource.createCollection(
                    collection
                )
            }
        }
    }

    suspend fun getMovies(): Result<List<Movie>, Exception> {
        return withContext(defaultDispatcher) { invokeCallWithBody { movieDataSource.getMovies() } }
    }

    suspend fun getMovie(id: Long): Result<Movie, Exception> {
        return withContext(defaultDispatcher) { invokeCallWithBody { movieDataSource.getMovie(id) } }
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
            invokeCallWithBody {
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
        private var INSTANCE: Repository? = null

        fun initialize(
            baseUrl: HttpUrl,
            movieDataSource: MovieDataSource,
            loginDataSource: LoginDataSource,
            collectionDataSource: CollectionDataSource
        ) {
            if (INSTANCE == null) {
                INSTANCE = Repository(
                    baseUrl,
                    movieDataSource = movieDataSource,
                    loginDataSource = loginDataSource,
                    collectionDataSource = collectionDataSource
                )
            }
        }

        fun get(): Repository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}
