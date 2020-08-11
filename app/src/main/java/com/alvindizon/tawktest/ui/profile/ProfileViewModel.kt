package com.alvindizon.tawktest.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alvindizon.tawktest.core.ui.BaseViewModel
import com.alvindizon.tawktest.data.db.GithubUser
import com.alvindizon.tawktest.domain.GetNoteUseCase
import com.alvindizon.tawktest.domain.InsertUserUseCase
import com.alvindizon.tawktest.domain.SearchUserUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProfileViewModel (private val searchUserUseCase: SearchUserUseCase,
    private val insertUserUseCase: InsertUserUseCase, private val getNoteUseCase: GetNoteUseCase)
        : BaseViewModel() {

    private val _uiState = MutableLiveData<ProfileUIState>()
    val uiState: LiveData<ProfileUIState>? get() = _uiState

    private val _dbState = MutableLiveData<DBState>()
    val dbState: LiveData<DBState>? get() = _dbState

    val followers = ObservableField<String>()
    val following = ObservableField<String>()
    val name = ObservableField<String>()
    val location = ObservableField<String>()

    fun fetchUserDetails(userName: String) {
        compositeDisposable += searchUserUseCase.searchForUser(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _uiState.value = LOADING }
            .subscribeBy(
                onSuccess = {
                    followers.set(it.followers.toString())
                    following.set(it.following.toString())
                    name.set(it.name)
                    location.set(it.location)
                    _uiState.value = SUCCESS
                },
                onError = { error ->
                    error.printStackTrace()
                    _uiState.value = error.message?.let {
                        ERROR(it)
                    }
                }
            )
    }

    fun saveNoteToDb(userName: String, note: String, url: String, avatarUrl: String) {
        compositeDisposable += insertUserUseCase.insert(GithubUser(userName, note, url, avatarUrl))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _dbState.value = DB_SAVING }
            .subscribeBy(
                onComplete = { _dbState.value = NOTE_SAVED },
                onError = {
                    _dbState.value = it.message?.let { msg -> DB_ERROR(msg) }
                    it.printStackTrace()
                }
            )
    }

    fun fetchNotesIfAny(userName: String): LiveData<String> {
        val note = MutableLiveData<String>()
        compositeDisposable += getNoteUseCase.getNoteByUserName(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { note.value = it },
                onError = { it.printStackTrace() }
            )
        return note
    }


}