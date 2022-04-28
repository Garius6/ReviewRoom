package com.toohome.android.reviewroom.data

import com.toohome.android.reviewroom.data.model.MovieCollection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CollectionDataSource(
    baseUrl: HttpUrl,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val collectionService = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(CollectionService::class.java)

    suspend fun getCollections() = collectionService.getCollections()
    suspend fun getCollection(id: Long) = collectionService.getCollection(id)
    suspend fun createCollection(collection: MovieCollection) =
        collectionService.createCollection(collection)
}
