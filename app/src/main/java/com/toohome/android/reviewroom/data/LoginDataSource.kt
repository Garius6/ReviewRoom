package com.toohome.android.reviewroom.data

import android.util.Log
import com.auth0.android.jwt.JWT
import com.toohome.android.reviewroom.data.model.LoggedUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val TAG = "LoginDataSource"

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(
    baseUrl: HttpUrl,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val loginService = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(LoginService::class.java)

    var tokenPair = TokenPair("", "")

    suspend fun login(username: String, password: String): Result<LoggedUser, Exception> {
        return withContext(dispatcher) {
            try {
                Log.d(TAG, "Login started")
                tokenPair = loginService.getTokenPair(username, password).body()
                    ?: throw IllegalArgumentException("user doesn't exist")
                Log.d(TAG, tokenPair.toString())
                val tokenString = tokenPair.accessToken

                Log.d(TAG, tokenString.length.toString())
                val jwt =
                    JWT(
                        tokenString
                    )

                val user = jwt.getClaim("id").asLong()?.let {
                    jwt.getClaim("username").asString()
                        ?.let { it1 -> LoggedUser(it, it1) }
                }
                SuccessResult(user ?: throw IllegalStateException("Cannot parse token"))
            } catch (e: Throwable) {
                Log.d(TAG, e.toString())
                ErrorResult(IOException("Error logging in", e))
            }
        }
    }

    fun getLoginClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            val originalRequest: Request = it.request()
            if (originalRequest.header("Authorization") != null) {
                it.proceed(originalRequest)
            }

            val authorizedRequest: Request = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${tokenPair.accessToken}")
                .method(originalRequest.method(), originalRequest.body())
                .build()
            it.proceed(authorizedRequest)
        }.authenticator(TokenAuthenticator()).build()
    }

    inner class TokenAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            val newAccessToken = runBlocking { loginService.refreshToken(tokenPair.refreshToken) }

            Log.d(TAG, "generate new token")
            return response.request().newBuilder().header(
                "Authorization",
                newAccessToken.body()?.accessToken
                    ?: throw IllegalStateException("new access token cannot be null")
            ).build()
        }
    }

    fun logout() {
        TODO()
    }
}
