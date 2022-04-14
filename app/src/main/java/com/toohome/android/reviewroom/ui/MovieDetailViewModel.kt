package com.toohome.android.reviewroom.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.toohome.android.reviewroom.model.MovieRepository

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(movieRepository: MovieRepository) :
    MovieBasicViewModel(movieRepository) {

    private val _selectedMovieId: MutableLiveData<Long> = MutableLiveData<Long>()
    val selectedMovieId = _selectedMovieId

    val movie = selectedMovieId.switchMap {
        liveData {
            emit(movieRepository.getMovie(it))
        }
    }
}
