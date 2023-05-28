package com.example.mtsinternship.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import org.json.JSONObject

class ApiServiceImpl : ApiService {
    // Не нашел API, где на бесплатном тарифе давали бы выбирать валюту, так что запрос относительно EUR.
    // Если купить тариф, тогда стоит добавить в запрос параметр &base=RUB
    val url = "http://api.exchangeratesapi.io/v1/latest?access_key=$apiKey"

    // Можно создать объект класс модели, содержащий все валюты, чтобы не парсить объект во вьюмодели,
    // но так как к ответу могут добавиться новые поля(наверное они могут добавить или убрать валюты?)
    // лучше парсить ответ в обработчике, чтобы гарантировано отобразить все данные.
    override fun getCurrencies(): Single<JSONObject> {
        return Rx2AndroidNetworking.get(url)
            .build()
            .jsonObjectSingle
    }
}
