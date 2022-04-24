package com.toohome.android.reviewroom.data

import android.util.Log
import com.auth0.android.jwt.JWT
import com.toohome.android.reviewroom.data.model.LoggedUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
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
        ScalarsConverterFactory.create()
    ).build().create(LoginService::class.java)
    var token = ""

    suspend fun login(username: String, password: String): Result<LoggedUser, Exception> {
        return withContext(dispatcher) {
            try {
                Log.d(TAG, "Login started")
                val response = loginService.loginOrCreate(username, password)
                val tokenString = response.body()
                    ?: throw IllegalStateException("token string cannot be null")
                token = tokenString

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

    fun logout() {
        TODO()
    }
}
