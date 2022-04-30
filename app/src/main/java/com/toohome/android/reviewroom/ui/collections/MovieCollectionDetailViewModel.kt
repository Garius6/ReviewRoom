package com.toohome.android.reviewroom.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.data.Result
import com.toohome.android.reviewroom.data.model.MovieCollection
import com.toohome.android.reviewroom.ui.MovieBasicViewModel
import kotlinx.coroutines.launch

class MovieCollectionDetailViewModel(private val repository: Repository) :
    MovieBasicViewModel(repository) {
    private val _collection: MutableLiveData<Result<MovieCollection, Exception>> =
        MutableLiveData<Result<MovieCollection, Exception>>()
    val collection: LiveData<Result<MovieCollection, Exception>> = _collection

    fun getCollection(id: Long) {
        viewModelScope.launch {
            _collection.value = repository.getCollection(id)
        }
    }
}
