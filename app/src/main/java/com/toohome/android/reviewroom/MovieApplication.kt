package com.toohome.android.reviewroom

import android.app.Application
import com.toohome.android.reviewroom.model.MovieRepository
import com.toohome.android.reviewroom.model.api.MovieApiFetcher
import okhttp3.HttpUrl

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val baseUrl = HttpUrl.Builder()
            .scheme("")
            .host("")
            .port()
            .build()

        val movieApiFetcher = MovieApiFetcher(baseUrl)
        MovieRepository.initialize(
            movieApiFetcher
        )
    }
}
