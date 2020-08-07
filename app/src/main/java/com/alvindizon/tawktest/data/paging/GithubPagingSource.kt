package com.alvindizon.tawktest.data.paging

import androidx.paging.rxjava2.RxPagingSource
import com.alvindizon.tawktest.data.db.UsersDao
import com.alvindizon.tawktest.ui.userlist.UsersListItem
import com.alvindizon.tawktest.data.networking.api.GithubApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val GITHUB_STARTING_USER_IDX = 0
private const val PAGE_SIZE = 20

class GithubPagingSource @Inject constructor(private val githubApi: GithubApi, private val dao: UsersDao)
    : RxPagingSource<Int, UsersListItem>(){

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, UsersListItem>> {
        val id = params.key ?: GITHUB_STARTING_USER_IDX
        return githubApi.getUsers(id, PAGE_SIZE)
        .subscribeOn(Schedulers.io())
            .map {
                it.map {item ->
                    UsersListItem(item.htmlUrl, item.login, item.avatarUrl, dao.checkIfUserExists(item.login))
                }
            }
            .map<LoadResult<Int, UsersListItem>> { item ->
                LoadResult.Page(
                    data = item,
                    prevKey = if (id == GITHUB_STARTING_USER_IDX) null else id - PAGE_SIZE,
                    nextKey =  if (item.isEmpty()) null else id + PAGE_SIZE
                )
            }
            .onErrorReturn { e -> LoadResult.Error(e) }
    }
}