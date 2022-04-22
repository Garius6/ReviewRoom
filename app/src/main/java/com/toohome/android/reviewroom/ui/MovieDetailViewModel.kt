package com.toohome.android.reviewroom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toohome.android.reviewroom.data.MovieRepository
import com.toohome.android.reviewroom.data.model.Movie
import com.toohome.android.reviewroom.utils.PendingResult
import com.toohome.android.reviewroom.utils.Result
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(private val movieRepository: MovieRepository) :
    MovieBasicViewModel(movieRepository) {

    private val _movie = MutableLiveData<Result<Movie, Exception>>(PendingResult)
    val movie: LiveData<Result<Movie, Exception>> = _movie

    fun getMovie(id: Long) {
        viewModelScope.launch {
            _movie.value = movieRepository.getMovie(id)
        }
    }
}
