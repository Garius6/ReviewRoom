package com.toohome.android.reviewroom.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay

private val testMovie1 = Movie(0, "test 1", "/some/url")

class MovieRepository {
    suspend fun getAllMovies(): LiveData<List<Movie>> {
        delay(2000)
        return MutableLiveData(listOf(testMovie1))
    }

    suspend fun getMovie(movieId: Long): LiveData<Movie> {
        delay(2000)
        return MutableLiveData(testMovie1)
    }
}
