package io.damo.androidstarter

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider

class StarterApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent(this)
    }
}

class AppComponent(app: Application) {
    val viewModelFactory = ViewModelProvider.AndroidViewModelFactory(app)
    val jokeApi = JokeApi(BuildConfig.API_URL)
}

val Context.appComponent: AppComponent
    get() = (applicationContext as StarterApp).appComponent
