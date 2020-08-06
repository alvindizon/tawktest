package com.alvindizon.tawktest.networking.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.alvindizon.tawktest.features.userlist.UsersListItem
import com.alvindizon.tawktest.networking.model.Item
import io.reactivex.Observable
import javax.inject.Inject

interface PagedListProvider {
    fun providePagedList(): Observable<PagingData<UsersListItem>>
}

class PagingProvider @Inject constructor(private val githubPagingSource: GithubPagingSource)
    : PagedListProvider{

    private fun pagedListObservable() = Pager(
        config = PagingConfig(20),
        remoteMediator = null,
        pagingSourceFactory = { githubPagingSource }
    ).observable

    override fun providePagedList(): Observable<PagingData<UsersListItem>> = pagedListObservable()

}