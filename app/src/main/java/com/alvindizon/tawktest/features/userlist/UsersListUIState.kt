package com.alvindizon.tawktest.features.userlist

import java.lang.Exception

sealed class UserListUIState

object LOADING: UserListUIState()

data class ERROR(
    var cause: Exception
): UserListUIState()

data class SUCCESS(val userList: List<UsersListItem>): UserListUIState()