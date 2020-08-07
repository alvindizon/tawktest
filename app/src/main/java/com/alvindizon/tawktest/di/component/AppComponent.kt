package com.alvindizon.tawktest.di.component

import com.alvindizon.tawktest.di.module.AppModule
import com.alvindizon.tawktest.di.module.DatabaseModule
import com.alvindizon.tawktest.di.module.NetworkModule
import com.alvindizon.tawktest.ui.profile.ProfileActivity
import com.alvindizon.tawktest.ui.userlist.UsersListActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DatabaseModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: UsersListActivity)

    fun inject(activity: ProfileActivity)
}