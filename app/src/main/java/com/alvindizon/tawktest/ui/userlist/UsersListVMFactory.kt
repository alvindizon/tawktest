package com.alvindizon.tawktest.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.tawktest.data.paging.PagingProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersListVMFactory @Inject constructor(private val pagingProvider: PagingProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersListViewModel::class.java)) {
            return UsersListViewModel(pagingProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}