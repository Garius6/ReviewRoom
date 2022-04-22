package com.toohome.android.reviewroom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toohome.android.reviewroom.data.PendingResult
import com.toohome.android.reviewroom.data.Result
import com.toohome.android.reviewroom.data.UserMovieRepository
import com.toohome.android.reviewroom.data.model.Movie
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(private val userMovieRepository: UserMovieRepository) :
    MovieBasicViewModel(userMovieRepository) {

    private val _movie = MutableLiveData<Result<Movie, Exception>>(PendingResult)
    val movie: LiveData<Result<Movie, Exception>> = _movie

    fun getMovie(id: Long) {
        viewModelScope.launch {
            _movie.value = userMovieRepository.getMovie(id)
        }
    }
}
