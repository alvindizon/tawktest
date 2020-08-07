package com.alvindizon.tawktest.di.module

import android.content.Context
import androidx.room.Room
import com.alvindizon.tawktest.data.db.UsersDao
import com.alvindizon.tawktest.data.db.UsersDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideUsersDb(appContext: Context) : UsersDb =
        Room.databaseBuilder(appContext, UsersDb::class.java, "tawktest-db")
            .build()

    @Provides
    @Singleton
    fun provideUsersDao(usersDb: UsersDb) : UsersDao = usersDb.usersDao()
}