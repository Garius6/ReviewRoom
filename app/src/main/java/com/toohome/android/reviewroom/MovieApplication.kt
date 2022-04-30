package com.toohome.android.reviewroom

import android.app.Application
import com.toohome.android.reviewroom.data.CollectionDataSource
import com.toohome.android.reviewroom.data.LoginDataSource
import com.toohome.android.reviewroom.data.MovieDataSource
import com.toohome.android.reviewroom.data.Repository
import okhttp3.HttpUrl

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val baseUrl = HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8000)
            .build()

        val loginDataSource = LoginDataSource(baseUrl)
        val movieApiFetcher = MovieDataSource()
        val collectionDataSource = CollectionDataSource()
        Repository.initialize(
            baseUrl,
            movieApiFetcher,
            loginDataSource,
            collectionDataSource
        )
    }
}
