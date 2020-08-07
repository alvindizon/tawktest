package com.alvindizon.tawktest.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.alvindizon.tawktest.core.ui.BaseViewModel
import com.alvindizon.tawktest.domain.GetUsersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class UsersListViewModel (private val getUsersUseCase: GetUsersUseCase) : BaseViewModel() {

    private val _uiState =  MutableLiveData<PagingData<UsersListItem>>()
    val uiState: LiveData<PagingData<UsersListItem>>? get() = _uiState

    // applies a filter to loaded items if search term is not null
    fun getUsers(filter: String?) {
        compositeDisposable += getUsersUseCase.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = { data ->
                    if(filter != null) {
                        _uiState.value = data.filterSync {
                            it.userName.contains(filter)
                        }
                    } else _uiState.value = data
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }
}