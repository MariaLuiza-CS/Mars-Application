package com.example.marsappkotlin.data.local.database

import android.content.Context
import androidx.room.Room

object MarsDatabaseBuild {

    private var INSTANCE: MarsDatabase? = null

    fun getInstance(context: Context): MarsDatabase {
        if (INSTANCE == null) {
            synchronized(MarsDatabase::class) {
                INSTANCE = buildRoomDb(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDb(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            MarsDatabase::class.java,
            MarsDatabase.DATABASE_NAME
        ).build()
}