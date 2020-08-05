package com.alvindizon.tawktest.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "userDb", indices = arrayOf(Index(value = ["username"],  unique = true)))
class GithubUser (
    var username: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
}