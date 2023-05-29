package com.example.mtsinternship.di

import com.example.mtsinternship.ui.main.CurrencyDetailsFragment
import com.example.mtsinternship.ui.main.CurrencyListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {
    fun inject(fragment: CurrencyListFragment)
    fun inject(fragment: CurrencyDetailsFragment)
}
