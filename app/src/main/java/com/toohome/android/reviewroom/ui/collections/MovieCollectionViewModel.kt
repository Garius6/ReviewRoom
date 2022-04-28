package com.toohome.android.reviewroom.ui.collections

import androidx.lifecycle.liveData
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.ui.MovieBasicViewModel

class MovieCollectionViewModel(private val repository: Repository) :
    MovieBasicViewModel(repository) {
    val collections = liveData {
        emit(repository.getCollections())
    }
}
