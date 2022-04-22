package com.toohome.android.reviewroom

import android.app.Application
import com.toohome.android.reviewroom.data.MovieRemoteDataSource
import com.toohome.android.reviewroom.data.MovieRepository
import okhttp3.HttpUrl

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val baseUrl = HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8000)
            .build()

        val movieApiFetcher = MovieRemoteDataSource(baseUrl)
        MovieRepository.initialize(
            movieApiFetcher
        )
    }
}
