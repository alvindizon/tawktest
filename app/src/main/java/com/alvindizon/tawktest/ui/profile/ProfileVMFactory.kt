package com.alvindizon.tawktest.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.tawktest.domain.GetNoteUseCase
import com.alvindizon.tawktest.domain.InsertUserUseCase
import com.alvindizon.tawktest.domain.SearchUserUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileVMFactory @Inject constructor(private val searchUserUseCase: SearchUserUseCase,
   private val insertUserUseCase: InsertUserUseCase, private val getNoteUseCase: GetNoteUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(searchUserUseCase, insertUserUseCase, getNoteUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}