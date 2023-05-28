package com.example.mtsinternship.data.api

import org.json.JSONObject
import io.reactivex.Single

interface ApiService {
    fun getCurrencies() : Single<JSONObject>
}