package com.alvindizon.tawktest.data

import com.alvindizon.tawktest.data.db.GithubUser
import com.alvindizon.tawktest.data.db.UsersDao
import com.alvindizon.tawktest.data.networking.api.GithubApi
import com.alvindizon.tawktest.data.networking.model.SearchUserResponse
import com.alvindizon.tawktest.domain.ProfileRepository
import io.reactivex.Completable
import io.reactivex.Single

class ProfileRepoImpl(private val githubApi: GithubApi, private val dao: UsersDao): ProfileRepository {

    override fun searchForUser(userName: String): Single<SearchUserResponse> = githubApi.searchForUser(userName)

    override fun insert(user: GithubUser): Completable = dao.insert(user)

    override fun getNoteByUserName(userName: String): Single<String> = dao.getNoteByUserName(userName)
}