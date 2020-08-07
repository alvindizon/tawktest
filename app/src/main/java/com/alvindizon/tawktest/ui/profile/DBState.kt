package com.alvindizon.tawktest.ui.profile

sealed class DBState

object DB_SAVING: DBState()

object NOTE_SAVED: DBState()

data class DB_ERROR(
    var errorMsg: String
): DBState()