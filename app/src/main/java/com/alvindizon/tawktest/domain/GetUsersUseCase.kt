package com.alvindizon.tawktest.domain

import androidx.paging.PagingData
import com.alvindizon.tawktest.ui.userlist.UsersListItem
import io.reactivex.Observable
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    fun getUsers(): Observable<PagingData<UsersListItem>> = usersRepository.getUsers()
}