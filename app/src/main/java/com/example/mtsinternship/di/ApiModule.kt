package com.example.mtsinternship.di

import com.example.mtsinternship.data.api.ApiService
import com.example.mtsinternship.data.api.ApiServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ApiModule {
    @Binds
    abstract fun provideApiService(serviceImpl: ApiServiceImpl) : ApiService
}