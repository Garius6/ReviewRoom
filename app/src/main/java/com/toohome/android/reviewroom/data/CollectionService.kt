package com.toohome.android.reviewroom.data

import com.toohome.android.reviewroom.data.model.MovieCollection
import retrofit2.Response
import retrofit2.http.*

enum class Filter {
    USER, TOP;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}

interface CollectionService {
    @GET("collections")
    suspend fun getCollections(@Query("filter") filter: Filter): Response<List<MovieCollection>>

    @GET("collection/{id}")
    suspend fun getCollection(@Path("id") id: Long): Response<MovieCollection>

    @POST("collection")
    suspend fun createCollection(@Body collection: MovieCollection): Response<Void>
}
