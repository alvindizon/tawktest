package com.alvindizon.tawktest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(GithubUser::class), version = 1)
abstract class UsersDb : RoomDatabase() {
    abstract fun usersDao() : UsersDao
}