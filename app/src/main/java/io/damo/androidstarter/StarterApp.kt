package io.damo.androidstarter

import android.app.Application
import android.content.Context

class StarterApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DefaultAppComponent(this)
    }
}

val Context.appComponent: AppComponent
    get() = (applicationContext as StarterApp).appComponent
