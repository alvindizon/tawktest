package com.alvindizon.tawktest.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.alvindizon.tawktest.core.ui.BaseViewModel
import com.alvindizon.tawktest.domain.GetUserByUserNameOrNote
import com.alvindizon.tawktest.domain.GetUsersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class UsersListViewModel (private val getUsersUseCase: GetUsersUseCase,
                          private val getUserByUserNameOrNote: GetUserByUserNameOrNote) : BaseViewModel() {

    private val _uiState =  MutableLiveData<PagingData<UsersListItem>>()
    val uiState: LiveData<PagingData<UsersListItem>>? get() = _uiState

    fun getUsers() {
        compositeDisposable += getUsersUseCase.getUsers()
            // NOTE: we call cachedIn, because if we don't this happens if night/dark mode is toggled:
            //    java.lang.IllegalStateException: Attempt to collect twice from pageEventFlow,
            //    which is an illegal operation. Did you forget to call Flow<PagingData<*>>.cachedIn(coroutineScope)?
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = { _uiState.value = it },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun searchUserOrNote(search: String) {
        compositeDisposable += getUserByUserNameOrNote.getUserByUserNameOrNote(search)
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = { _uiState.value = it },
                onError = {
                    it.printStackTrace()
                }
            )
    }
}