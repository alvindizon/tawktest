package com.alvindizon.tawktest.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.tawktest.db.UsersDao
import com.alvindizon.tawktest.networking.api.GithubApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileVMFactory @Inject constructor(private val githubApi: GithubApi, private val usersDao: UsersDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(githubApi, usersDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}