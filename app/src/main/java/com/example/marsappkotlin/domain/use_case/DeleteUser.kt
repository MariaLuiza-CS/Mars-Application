package com.example.marsappkotlin.domain.use_case

import com.example.marsappkotlin.data.local.model.User
import com.example.marsappkotlin.domain.LocalRepositoty

class DeleteUser(private val localRepositoty: LocalRepositoty) {

    suspend operator fun invoke(user: User) {
        localRepositoty.deleteUser(user)
    }
}