package com.alvindizon.tawktest.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (private val application: Application){

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}