package com.toohome.android.reviewroom.ui

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.model.MovieRepository

private const val TAG = "MovieBasicViewModel"

open class MovieBasicViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val baseUrl = movieRepository.baseUrl

    fun downloadInto(posterUrl: String, into: ImageView) {
        val posterUrlString = "${baseUrl}$posterUrl"
        Log.d(TAG, "Download poster from $posterUrlString")
        Picasso.get().load(posterUrlString).placeholder(R.drawable.ic_poster_placeholder).into(into)
    }
}
