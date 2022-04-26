package com.toohome.android.reviewroom.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class TokenPair(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)

interface LoginService {
    @GET("auth/token")
    suspend fun getTokenPair(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<TokenPair>

    @POST("auth/token/refresh")
    suspend fun refreshToken(@Body token: String): Response<TokenPair>
}
