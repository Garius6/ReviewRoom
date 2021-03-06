package com.toohome.android.reviewroom.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.data.Result
import com.toohome.android.reviewroom.data.model.Movie
import com.toohome.android.reviewroom.ui.MovieBasicViewModel
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(private val repository: Repository) :
    MovieBasicViewModel(repository) {

    private val _movie = MutableLiveData<Result<Movie, Exception>>(Result.Pending)
    val movie: LiveData<Result<Movie, Exception>> = _movie

    fun getMovie(id: Long) {
        viewModelScope.launch {
            _movie.value = repository.getMovie(id)
        }
    }
}
