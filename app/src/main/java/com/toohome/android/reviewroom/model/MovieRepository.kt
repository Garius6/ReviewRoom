package com.toohome.android.reviewroom.model

import com.toohome.android.reviewroom.model.api.MovieApiFetcher
import com.toohome.android.reviewroom.utils.ErrorResult
import com.toohome.android.reviewroom.utils.Result
import com.toohome.android.reviewroom.utils.SuccessResult
import okhttp3.HttpUrl
import retrofit2.Response

class MovieRepository(baseUrl: HttpUrl) {
    private val movieApiFetcher: MovieApiFetcher = MovieApiFetcher(baseUrl)

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
        return invokeCall { movieApiFetcher.getMovies() }
    }

    suspend fun getMovie(id: Long): Result<Movie, Exception> {
        return invokeCall { movieApiFetcher.getMovie(id) }
    }

    companion object {
        private var INSTANCE: MovieRepository? = null

        fun initialize(baseUrl: HttpUrl) {
            if (INSTANCE == null) {
                INSTANCE = MovieRepository(baseUrl)
            }
        }

        fun get(): MovieRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}
