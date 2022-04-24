package com.toohome.android.reviewroom.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {
    @GET("user/loginOrCreate")
    suspend fun loginOrCreate(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<String>
}
