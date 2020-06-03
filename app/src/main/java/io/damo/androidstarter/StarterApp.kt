package io.damo.androidstarter

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider

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

val FragmentActivity.viewModelProvider: ViewModelProvider
    get() = ViewModelProvider(viewModelStore, appComponent.viewModelFactory)

val Fragment.activityViewModelProvider: ViewModelProvider
    get() = requireActivity().viewModelProvider

val Fragment.fragmentViewModelProvider: ViewModelProvider
    get() = ViewModelProvider(viewModelStore, appComponent.viewModelFactory)
