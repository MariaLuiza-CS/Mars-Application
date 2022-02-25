package com.example.marsappkotlin.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marsappkotlin.data.local.dao.UserDao
import com.example.marsappkotlin.data.local.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class MarsDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        const val DATABASE_NAME = "users_db"
    }
}