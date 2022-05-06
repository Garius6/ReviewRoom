package com.toohome.android.reviewroom.ui.movie

import androidx.lifecycle.liveData
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.ui.MovieBasicViewModel

class MovieListViewModel(repository: Repository) :
    MovieBasicViewModel(repository) {
    val movies = liveData { emit(repository.getMovies()) }
}
