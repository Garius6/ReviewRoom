package com.toohome.android.reviewroom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.ui.collections.MovieCollectionDetailViewModel
import com.toohome.android.reviewroom.ui.collections.MovieCollectionViewModel
import com.toohome.android.reviewroom.ui.login.LoginViewModel
import com.toohome.android.reviewroom.ui.user.UserDetailViewModel

class MovieViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        when (modelClass) {
            MovieDetailViewModel::class.java -> return MovieDetailViewModel(Repository.get()) as T
            MovieListViewModel::class.java -> return MovieListViewModel(Repository.get()) as T
            LoginViewModel::class.java -> return LoginViewModel(Repository.get()) as T
            MovieCollectionViewModel::class.java -> return MovieCollectionViewModel(Repository.get()) as T
            MovieCollectionDetailViewModel::class.java -> return MovieCollectionDetailViewModel(Repository.get()) as T
            UserDetailViewModel::class.java -> return UserDetailViewModel(Repository.get()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
