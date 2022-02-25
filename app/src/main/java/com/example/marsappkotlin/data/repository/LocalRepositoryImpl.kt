package com.example.marsappkotlin.data.repository

import com.example.marsappkotlin.data.local.dao.UserDao
import com.example.marsappkotlin.data.local.database.MarsDatabase
import com.example.marsappkotlin.data.local.model.User
import com.example.marsappkotlin.domain.LocalRepositoty

class LocalRepositoryImpl(private val marsDatabase: MarsDatabase) : LocalRepositoty {

    override suspend fun getAllUsers(): List<User> {
        return marsDatabase.userDao().getAllUsers()
    }

    override suspend fun insertUser(user: User) {
        return marsDatabase.userDao().insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        return marsDatabase.userDao().deleteUser(user)
    }
}