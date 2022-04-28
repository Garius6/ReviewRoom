package com.toohome.android.reviewroom.ui

import androidx.lifecycle.liveData
import com.toohome.android.reviewroom.data.Repository

class MovieListViewModel(repository: Repository) :
    MovieBasicViewModel(repository) {
    val movies = liveData { emit(repository.getMovies()) }
}
