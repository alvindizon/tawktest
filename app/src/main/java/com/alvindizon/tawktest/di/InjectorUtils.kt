package com.alvindizon.tawktest.di

import com.alvindizon.tawktest.TawkTestApp
import com.alvindizon.tawktest.di.component.AppComponent

object InjectorUtils {

    @JvmStatic
    fun getAppComponent(): AppComponent = TawkTestApp.getAppComponent()

}