package com.example.mtsinternship.viewmodel

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class CurrencyDetailsViewModel @Inject constructor() {

    val convertedValue = MutableLiveData<Double>()
    var exchangeRate: Float = 0.0f
    var currencyName: String = ""
    fun convertValue(value: Double) {
        convertedValue.postValue(exchangeRate * value)
    }
}
