package com.toohome.android.reviewroom.data

sealed class Result<out Success, out Failure> {

    object Pending : Result<Nothing, Nothing>()

    data class Success<out Success>(
        val data: Success
    ) : Result<Success, Nothing>()

    data class Failure<Failure>(
        val error: Exception
    ) : Result<Nothing, Failure>()
}
