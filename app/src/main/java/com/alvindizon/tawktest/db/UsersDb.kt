package com.alvindizon.tawktest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alvindizon.tawktest.db.GithubUser
import com.alvindizon.tawktest.db.UsersDao

@Database(entities = arrayOf(GithubUser::class), version = 1)
abstract class UsersDb : RoomDatabase() {
    abstract fun usersDao() : UsersDao
}