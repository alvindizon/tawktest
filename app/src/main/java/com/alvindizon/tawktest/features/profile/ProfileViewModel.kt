package com.alvindizon.tawktest.features.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alvindizon.tawktest.core.ui.BaseViewModel
import com.alvindizon.tawktest.db.GithubUser
import com.alvindizon.tawktest.db.UsersDao
import com.alvindizon.tawktest.networking.api.GithubApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val githubApi: GithubApi, private val dao: UsersDao)
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
        compositeDisposable.add(githubApi.searchForUser(userName)
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
        )
    }

    fun saveNoteToDb(userName: String, note: String) {
        compositeDisposable.add(dao.insert(GithubUser(userName, note))
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
        )
    }


}