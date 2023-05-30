package com.example.mtsinternship.di

import com.example.mtsinternship.data.api.ApiService
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import org.json.JSONObject
import org.mockito.Mockito

@Module
class TestApiModule {

    private val jsonRaw = "{\n" +
            "  \"success\":true,\n" +
            "  \"rates\":{\n" +
            "    \"RUB\":10,\n" +
            "    \"USD\":1.07\n" +
            "  }\n" +
            "}"

    @Provides
    fun provideApiService() : ApiService {
        val apiService = Mockito.mock(ApiService::class.java)
        Mockito.`when`(apiService.getCurrencies()).thenReturn(Single.just(JSONObject(jsonRaw)))
        return apiService
    }
}