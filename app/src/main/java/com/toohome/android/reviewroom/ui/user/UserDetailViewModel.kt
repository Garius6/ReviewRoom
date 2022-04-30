package com.toohome.android.reviewroom.ui.user

import com.toohome.android.reviewroom.data.Repository
import com.toohome.android.reviewroom.data.model.LoggedUser
import com.toohome.android.reviewroom.ui.MovieBasicViewModel

class UserDetailViewModel(private val repository: Repository) : MovieBasicViewModel(repository) {
    fun getUser(): LoggedUser {
        return repository.user ?: TODO()
    }
}
