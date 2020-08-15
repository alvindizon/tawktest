package com.alvindizon.tawktest.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
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