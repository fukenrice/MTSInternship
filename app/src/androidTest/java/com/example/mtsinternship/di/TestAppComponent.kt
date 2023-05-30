package com.example.mtsinternship.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestApiModule::class])
interface TestAppComponent : AppComponent