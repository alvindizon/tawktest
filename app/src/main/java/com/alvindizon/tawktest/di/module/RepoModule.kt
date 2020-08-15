package com.alvindizon.tawktest.di.module

import com.alvindizon.tawktest.data.ProfileRepoImpl
import com.alvindizon.tawktest.data.UsersRepoImpl
import com.alvindizon.tawktest.data.db.UsersDao
import com.alvindizon.tawktest.data.networking.api.GithubApi
import com.alvindizon.tawktest.data.paging.GithubPagingSource
import com.alvindizon.tawktest.domain.ProfileRepository
import com.alvindizon.tawktest.domain.UsersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideUsersRepo(githubPagingSource: GithubPagingSource, dao: UsersDao): UsersRepository
            = UsersRepoImpl(githubPagingSource, dao)


    @Provides
    @Singleton
    fun provideProfileRepo(githubApi: GithubApi, usersDao: UsersDao): ProfileRepository
            = ProfileRepoImpl(githubApi, usersDao)
}