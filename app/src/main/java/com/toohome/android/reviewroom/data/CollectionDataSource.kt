package com.toohome.android.reviewroom.data

import com.toohome.android.reviewroom.data.model.MovieCollection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.create

class CollectionDataSource(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private lateinit var collectionService: CollectionService

    fun setRetrofit(retrofit: Retrofit) {
        collectionService = retrofit.create(CollectionService::class.java)
    }

    suspend fun getCollections(filter: Filter) = collectionService.getCollections(filter)
    suspend fun getCollection(id: Long) = collectionService.getCollection(id)
    suspend fun createCollection(collection: MovieCollection) =
        collectionService.createCollection(collection)
}
