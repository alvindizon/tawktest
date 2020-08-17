package com.alvindizon.tawktest.domain

interface PreferencesRepository {

    fun toggleNightMode()

    fun getNightMode(): Int
}