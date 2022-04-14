package com.toohome.android.reviewroom.utils

sealed class Result<out Success, out Failure>

object PendingResult : Result<Nothing, Nothing>()

data class SuccessResult<out Success>(
    val data: Success
) : Result<Success, Nothing>()

data class ErrorResult<Failure>(
    val error: Exception
) : Result<Nothing, Failure>()
