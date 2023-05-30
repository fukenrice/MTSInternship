package com.example.mtsinternship.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mtsinternship.data.api.ApiService
import com.example.mtsinternship.data.model.CurrencyModel
import com.example.mtsinternship.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyListViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {
    private val shownCurrencyList =
        MutableLiveData<Resource<List<CurrencyModel>>>(Resource.loading(null))
    private var currencyList: List<CurrencyModel> = listOf()

    private val TAG = "CurrencyListViewModel"

    @SuppressLint("CheckResult")
    fun getCurrencies() {
        shownCurrencyList.postValue(Resource.loading(null))
        apiService.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    val newList = mutableListOf<CurrencyModel>()
                    val rates = response.get("rates") as JSONObject
                    rates.keys().forEachRemaining { key ->
                        newList.add(CurrencyModel(key, rates.get(key).toString().toDouble()))
                    }
                    currencyList = newList.toMutableList()
                    shownCurrencyList.postValue(Resource.success(newList.toMutableList()))
                },
                { error ->
                    Log.d(TAG, "getCurrencies: " + error.message.toString())
                    shownCurrencyList.postValue(Resource.error(error.message.toString(),
                        shownCurrencyList.value?.data
                    ))
                }
            )
    }

    fun getCurrencyList() = shownCurrencyList

    fun filterList(query: String) {
        shownCurrencyList.postValue(Resource.loading(shownCurrencyList.value?.data))
        val filteredList = currencyList.filter { it.name.startsWith(query) }
        shownCurrencyList.postValue(Resource.success(filteredList))
    }

    fun isEmpty() = currencyList.isEmpty()
}
