package com.swvl.tasksearch

import com.swvl.tasksearch.di.networkModule
import com.swvl.tasksearch.di.repositoryModule
import com.swvl.tasksearch.di.viewModelModule
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class TaskSearchApp : LocaleAwareApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TaskSearchApp)
            androidFileProperties()
            // modules
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}