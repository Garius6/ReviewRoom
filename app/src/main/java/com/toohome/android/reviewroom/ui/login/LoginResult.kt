package com.toohome.android.reviewroom.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: Int? = null,
    val error: Int? = null
)
