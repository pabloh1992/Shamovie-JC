package com.pablodev.shamovie

import android.app.Application
import com.pablodev.shamovie.di.initKoin
import org.koin.android.ext.koin.androidContext


class ShamovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ShamovieApplication)
        }
    }
}