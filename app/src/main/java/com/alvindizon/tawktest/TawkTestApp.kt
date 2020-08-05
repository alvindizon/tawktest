package com.alvindizon.tawktest

import android.app.Application
import com.alvindizon.tawktest.di.component.AppComponent
import com.alvindizon.tawktest.di.component.DaggerAppComponent
import com.alvindizon.tawktest.di.module.AppModule

class TawkTestApp: Application() {

    companion object {
        private var INSTANCE: TawkTestApp? = null

        @JvmStatic
        fun get(): TawkTestApp = INSTANCE!!

        @JvmStatic
        fun getAppComponent() = get().appComponent
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}