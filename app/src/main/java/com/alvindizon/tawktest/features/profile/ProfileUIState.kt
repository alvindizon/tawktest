package com.alvindizon.tawktest.features.profile

sealed class ProfileUIState

object LOADING: ProfileUIState()

object SUCCESS: ProfileUIState()

data class ERROR(
    var errorMsg: String
): ProfileUIState()