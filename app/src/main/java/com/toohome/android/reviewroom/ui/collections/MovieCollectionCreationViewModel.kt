package com.toohome.android.reviewroom.ui.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.data.model.Movie
import com.toohome.android.reviewroom.data.model.MovieCollection
import com.toohome.android.reviewroom.ui.MovieBasicViewModel
import kotlinx.coroutines.launch

private const val TAG = "MovieCollectionPicker"

class MoviePickerViewModel(private val repository: Repository) : MovieBasicViewModel(repository) {
    private val _selectedMovies: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val selectedMovies: LiveData<List<Movie>> = _selectedMovies

    private val _collectionName: MutableLiveData<String> = MutableLiveData()
    val collectionName: LiveData<String> = _collectionName

    val movies = liveData { emit(repository.getMovies()) }

    fun addMovie(movie: Movie) {
        val oldList = _selectedMovies.value?.toMutableList()
        oldList?.add(movie)
        Log.d(TAG, "$movie $oldList")
        _selectedMovies.value = oldList!!
    }

    fun createCollection() {
        viewModelScope.launch {
            repository.createCollection(
                MovieCollection(
                    name = _collectionName.value!!,
                    movies = selectedMovies.value!!,
                    authorId = repository.user?.id!!
                )
            )
        }
    }

    fun collectionNameChanged(newName: String) {
        _collectionName.value = newName
    }
}
