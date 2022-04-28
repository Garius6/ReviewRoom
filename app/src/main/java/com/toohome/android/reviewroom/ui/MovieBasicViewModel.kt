package com.toohome.android.reviewroom.ui

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.data.model.Movie

private const val TAG = "MovieBasicViewModel"

open class MovieBasicViewModel(private val repository: Repository) : ViewModel() {

    fun loadPosterInto(movie: Movie, into: ImageView) {
        Log.d(TAG, "Download poster for $movie")
        repository.loadPostIntoImageView(movie, into)
    }
}
