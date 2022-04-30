package com.toohome.android.reviewroom.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toohome.android.reviewroom.data.*
import com.toohome.android.reviewroom.data.model.MovieCollection
import com.toohome.android.reviewroom.ui.MovieBasicViewModel
import kotlinx.coroutines.launch
import java.util.*

class MovieCollectionViewModel(private val repository: Repository) :
    MovieBasicViewModel(repository) {
    private val _collectionsUI: MutableLiveData<MovieCollectionUI> =
        MutableLiveData<MovieCollectionUI>()
    val collectionsUI: LiveData<MovieCollectionUI> = _collectionsUI

    fun getCollections(userId: Long) {
        viewModelScope.launch {
            val isUsersCollection = userId != -1L
            when (
                val res =
                    repository.getCollections(if (isUsersCollection) Filter.USER else Filter.TOP)
            ) {
                is SuccessResult -> {
                    _collectionsUI.value = MovieCollectionUI(isUsersCollection, null, res.data)
                }
                is ErrorResult -> {
                    _collectionsUI.value = MovieCollectionUI(isUsersCollection, res.error, null)
                }
                is PendingResult -> {
                    TODO()
                }
            }
        }
    }
}

data class MovieCollectionUI(
    val isUsersCollections: Boolean,
    val error: Exception?,
    val collections: List<MovieCollection>?
)