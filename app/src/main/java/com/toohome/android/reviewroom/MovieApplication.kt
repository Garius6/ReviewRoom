package com.toohome.android.reviewroom

import android.app.Application
import com.toohome.android.reviewroom.model.MovieRepository
import okhttp3.HttpUrl

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MovieRepository.initialize(
            HttpUrl.Builder()
                .scheme("http")
                .host("")
                .port(8000)
                .build()
        )
    }
}
