package io.damo.androidstarter

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment

class StarterApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DefaultAppComponent(this)
    }
}

val Context.appComponent: AppComponent
    get() = (applicationContext as StarterApp).appComponent

val Fragment.appComponent: AppComponent
    get() = requireContext().appComponent
