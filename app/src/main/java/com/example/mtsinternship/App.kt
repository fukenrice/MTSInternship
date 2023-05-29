package com.example.mtsinternship

import android.app.Application
import com.example.mtsinternship.di.AppComponent
import com.example.mtsinternship.di.DaggerAppComponent

open class App : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.create()
    }
}