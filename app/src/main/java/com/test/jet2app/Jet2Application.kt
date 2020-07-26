package com.test.jet2app

import android.app.Application
import com.test.jet2app.database.ArticlesRoomDB
import com.test.jet2app.injection.module.networkModule
import com.test.jet2app.injection.module.repositoryModule
import com.test.jet2app.injection.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Jet2Application : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@Jet2Application)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }

    fun getRoomDAO(): ArticlesRoomDB {
        return ArticlesRoomDB.getDatabase(this)
    }

    companion object {
        @get:Synchronized
        var instance: Jet2Application? = null
            private set
    }
}