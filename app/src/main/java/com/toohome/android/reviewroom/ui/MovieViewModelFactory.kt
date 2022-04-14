package com.toohome.android.reviewroom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toohome.android.reviewroom.model.MovieRepository

class MovieViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        when (modelClass) {
            MovieDetailViewModel::class.java -> return MovieDetailViewModel(MovieRepository.get()) as T
            MovieListViewModel::class.java -> return MovieListViewModel(MovieRepository.get()) as T
        }
        throw IllegalStateException()
    }
}
