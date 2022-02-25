package com.example.marsappkotlin.domain.use_case

import com.example.marsappkotlin.data.local.model.User
import com.example.marsappkotlin.domain.RemoteRepository

class GetUserProfileFirebase(private val remoteRepository: RemoteRepository) {
//    suspend operator fun invoke(): Pair<String, User> {
//        return remoteRepository.getUserProfile()
//    }
}