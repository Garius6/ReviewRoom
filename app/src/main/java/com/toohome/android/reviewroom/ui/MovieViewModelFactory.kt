package com.toohome.android.reviewroom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toohome.android.reviewroom.data.UserMovieRepository
import com.toohome.android.reviewroom.ui.login.LoginViewModel

class MovieViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        when (modelClass) {
            MovieDetailViewModel::class.java -> return MovieDetailViewModel(UserMovieRepository.get()) as T
            MovieListViewModel::class.java -> return MovieListViewModel(UserMovieRepository.get()) as T
            LoginViewModel::class.java -> return LoginViewModel(UserMovieRepository.get()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
