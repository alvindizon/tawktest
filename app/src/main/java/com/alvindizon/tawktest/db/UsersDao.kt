package com.alvindizon.tawktest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alvindizon.tawktest.db.GithubUser
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class UsersDao {

    @Insert
    abstract fun insert(user: GithubUser)

    @Insert
    abstract fun insert(userList: List<GithubUser>)

    @Query("DELETE FROM userDb")
    abstract fun clearDb()

    @Query("SELECT * FROM userDb WHERE username LIKE :username")
    abstract fun getUserByUserName(username: String):List<GithubUser>

    @Query("SELECT * FROM userDb")
    abstract fun getAllUsers(): List<GithubUser>


}