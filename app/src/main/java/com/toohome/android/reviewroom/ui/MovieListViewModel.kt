package com.toohome.android.reviewroom.ui

import androidx.lifecycle.liveData
import com.toohome.android.reviewroom.data.MovieRepository

class MovieListViewModel(movieRepository: MovieRepository) : MovieBasicViewModel(movieRepository) {
    val movies = liveData { emit(movieRepository.getMovies()) }
}
