package com.alvindizon.tawktest.domain

import androidx.paging.PagingData
import com.alvindizon.tawktest.ui.userlist.UsersListItem
import io.reactivex.Observable
import javax.inject.Inject

class GetUserByUserNameOrNote @Inject constructor(private val usersRepository: UsersRepository) {

    fun getUserByUserNameOrNote(filter: String): Observable<PagingData<UsersListItem>> =
        usersRepository.getUserByUserNameOrNote(filter)
}