package com.example.marsappkotlin.domain

import com.example.marsappkotlin.data.local.model.User

interface LocalRepositoty {

    //USER
    suspend fun getAllUsers(): List<User>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
}