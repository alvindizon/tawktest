package com.alvindizon.tawktest.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.alvindizon.tawktest.core.ui.BaseViewModel
import com.alvindizon.tawktest.data.paging.PagingProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersListViewModel @Inject constructor(private val pagingProvider: PagingProvider) : BaseViewModel() {

    private val _uiState =  MutableLiveData<PagingData<UsersListItem>>()
    val uiState: LiveData<PagingData<UsersListItem>>? get() = _uiState

    fun fetchUsers() {
        compositeDisposable.add(pagingProvider.providePagedList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    _uiState.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
        )
    }
}