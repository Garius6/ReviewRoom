package com.toohome.android.reviewroom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toohome.android.reviewroom.model.Movie

class MovieDetailViewModel : ViewModel() {

    private val _movie: MutableLiveData<Movie> = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie
}
