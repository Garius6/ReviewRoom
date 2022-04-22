package com.toohome.android.reviewroom.ui

import androidx.lifecycle.liveData
import com.toohome.android.reviewroom.data.UserMovieRepository

class MovieListViewModel(userMovieRepository: UserMovieRepository) :
    MovieBasicViewModel(userMovieRepository) {
    val movies = liveData { emit(userMovieRepository.getMovies()) }
}
