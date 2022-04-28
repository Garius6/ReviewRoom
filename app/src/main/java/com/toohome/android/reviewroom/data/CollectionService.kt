package com.toohome.android.reviewroom.data

import com.toohome.android.reviewroom.data.model.MovieCollection
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CollectionService {
    @GET("collections")
    suspend fun getCollections(): Response<List<MovieCollection>>

    @GET("collection/{id}")
    suspend fun getCollection(@Path("id") id: Long): Response<MovieCollection>

    @POST("collection")
    suspend fun createCollection(@Body collection: MovieCollection): Response<Nothing>
}
