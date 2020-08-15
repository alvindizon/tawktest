package com.alvindizon.tawktest.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: GithubUser): Completable

    @Insert
    abstract fun insert(userList: List<GithubUser>): Completable

    @Query("DELETE FROM userDb")
    abstract fun clearDb(): Completable

    @Query("SELECT * FROM userDb")
    abstract fun getAllUsers(): Single<List<GithubUser>>

    @Query("SELECT EXISTS(SELECT 1 FROM userDb WHERE username LIKE :userName)")
    abstract fun checkIfUserExists(userName: String): Boolean

    @Query("SELECT note FROM userDb WHERE username LIKE :userName")
    abstract fun getNoteByUserName(userName: String): Single<String>

    @Query("SELECT * FROM userDb WHERE note LIKE :filter OR userName LIKE :filter")
    abstract fun getUserByUserNameOrNote(filter: String): PagingSource<Int, GithubUser>

}