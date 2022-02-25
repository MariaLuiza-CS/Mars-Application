package com.example.marsappkotlin.data.local.dao

import androidx.room.*
import com.example.marsappkotlin.data.local.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

}