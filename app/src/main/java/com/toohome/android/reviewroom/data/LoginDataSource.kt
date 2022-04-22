package com.toohome.android.reviewroom.data

import android.util.Log
import com.auth0.android.jwt.JWT
import com.toohome.android.reviewroom.data.model.LoggedUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private const val TAG = "LoginDataSource"

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(
    private val baseUrl: HttpUrl,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun login(username: String, password: String): Result<LoggedUser, Exception> {
        return withContext(dispatcher) {
            try {

                val url = baseUrl.newBuilder().addPathSegment("user/loginOrCreate")
                    .addQueryParameter("username", username)
                    .addQueryParameter("password", password).build()
                val client = OkHttpClient()
                val request = Request.Builder().url(url).get().build()
                val response = client.newCall(request).execute()
                val tokenString = response.body()?.string()
                println(tokenString)
                Log.d(TAG, tokenString ?: "tokenString is null")
                val token =
                    JWT(
                        tokenString ?: throw IllegalArgumentException("token string cannot be null")
                    )

                val fakeUser = token.getClaim("id").asLong()?.let {
                    token.getClaim("username").asString()
                        ?.let { it1 -> LoggedUser(it, it1) }
                }
                SuccessResult(fakeUser ?: throw IllegalStateException("Cannot parse token"))
            } catch (e: Throwable) {
                Log.d(TAG, e.toString())
                ErrorResult(IOException("Error logging in", e))
            }
        }
    }

    fun logout() {
        TODO()
    }
}
