package com.example.marsappkotlin.domain.use_case

import com.example.marsappkotlin.data.local.model.User
import com.example.marsappkotlin.domain.LocalRepositoty

class GetUser(private val localRepository: LocalRepositoty) {

    suspend operator fun invoke(): List<User> {
        return localRepository.getAllUsers()
    }

}