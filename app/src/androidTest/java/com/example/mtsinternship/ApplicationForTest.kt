package com.example.mtsinternship

import com.example.mtsinternship.di.AppComponent
import com.example.mtsinternship.di.DaggerTestAppComponent

class ApplicationForTest : App() {
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.create()
    }
}