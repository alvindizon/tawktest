package com.alvindizon.tawktest.domain

import androidx.paging.PagingData
import com.alvindizon.tawktest.ui.userlist.UsersListItem
import io.reactivex.Observable

interface UsersRepository {

    fun getUsers(): Observable<PagingData<UsersListItem>>

    fun getUserByUserNameOrNote(filter: String): Observable<PagingData<UsersListItem>>
}