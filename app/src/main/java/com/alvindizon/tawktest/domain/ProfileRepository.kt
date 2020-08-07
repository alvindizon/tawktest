package com.alvindizon.tawktest.domain

import com.alvindizon.tawktest.data.db.GithubUser
import com.alvindizon.tawktest.data.networking.model.SearchUserResponse
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileRepository {

    fun searchForUser(userName: String): Single<SearchUserResponse>

    fun insert(user: GithubUser): Completable

    fun getNoteByUserName(userName: String): Single<String>
}