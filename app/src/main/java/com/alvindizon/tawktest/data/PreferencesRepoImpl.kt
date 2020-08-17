package com.alvindizon.tawktest.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import com.alvindizon.tawktest.core.Const
import com.alvindizon.tawktest.domain.PreferencesRepository

class PreferencesRepoImpl (private val sharedPreferences: SharedPreferences): PreferencesRepository{

    override fun toggleNightMode() {
        val mode = if(getNightMode() == MODE_NIGHT_YES) MODE_NIGHT_NO else MODE_NIGHT_YES
        sharedPreferences.edit().putInt(Const.KEY_NIGHT_MODE, mode).apply()

        setDefaultNightMode(mode)
    }

    override fun getNightMode(): Int = sharedPreferences.getInt(Const.KEY_NIGHT_MODE, MODE_NIGHT_NO)
}