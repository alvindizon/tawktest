package com.alvindizon.tawktest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.alvindizon.tawktest.data.paging.GithubPagingSource
import com.alvindizon.tawktest.domain.UsersRepository
import com.alvindizon.tawktest.ui.userlist.UsersListItem
import io.reactivex.Observable

class UsersRepoImpl (private val githubPagingSource: GithubPagingSource): UsersRepository{

    override fun getUsers(): Observable<PagingData<UsersListItem>> = Pager(
        config = PagingConfig(20),
        remoteMediator = null,
        pagingSourceFactory = { githubPagingSource }
    ).observable
}