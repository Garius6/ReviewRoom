package com.toohome.android.reviewroom.ui.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.data.model.Movie
import com.toohome.android.reviewroom.ui.MovieBasicViewModel

private const val TAG = "MovieCollectionPicker"

class MoviePickerViewModel(private val repository: Repository) : MovieBasicViewModel(repository) {
    private val _selectedMovies: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val selectedMovies: LiveData<List<Movie>> = _selectedMovies

    val movies = liveData { emit(repository.getMovies()) }

    fun addMovie(movie: Movie) {
        val oldList = _selectedMovies.value?.toMutableList()
        oldList?.add(movie)
        Log.d(TAG, "$movie $oldList")
        _selectedMovies.value = oldList!!
    }
}
