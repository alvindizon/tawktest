package com.alvindizon.tawktest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class UsersDao {

    @Insert
    abstract fun insert(user: GithubUser): Completable

    @Insert
    abstract fun insert(userList: List<GithubUser>): Completable

    @Query("DELETE FROM userDb")
    abstract fun clearDb(): Completable

    @Query("SELECT * FROM userDb")
    abstract fun getAllUsers(): Single<List<GithubUser>>

    @Query("SELECT EXISTS(SELECT * FROM userDb WHERE username LIKE :username)")
    abstract fun checkIfUserExists(username: String): Boolean

}