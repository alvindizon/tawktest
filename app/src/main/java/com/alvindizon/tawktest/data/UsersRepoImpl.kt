package com.alvindizon.tawktest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.observable
import com.alvindizon.tawktest.data.db.UsersDao
import com.alvindizon.tawktest.data.paging.GithubPagingSource
import com.alvindizon.tawktest.domain.UsersRepository
import com.alvindizon.tawktest.ui.userlist.UsersListItem
import io.reactivex.Observable

class UsersRepoImpl (private val githubPagingSource: GithubPagingSource, private val dao: UsersDao):
    UsersRepository{

    override fun getUsers(): Observable<PagingData<UsersListItem>> = Pager(
        config = PagingConfig(20),
        remoteMediator = null,
        pagingSourceFactory = { githubPagingSource }
    ).observable

    override fun getUserByUserNameOrNote(filter: String): Observable<PagingData<UsersListItem>> =
        Pager(
            config = PagingConfig(20),
            remoteMediator = null,
            pagingSourceFactory = { dao.getUserByUserNameOrNote(filter) }
        ).observable.map { pagingData ->
            pagingData.map {githubUser ->
                UsersListItem(
                    githubUser.url,
                    githubUser.username,
                    githubUser.avatarUrl,
                    true
                )
            }
        }
}