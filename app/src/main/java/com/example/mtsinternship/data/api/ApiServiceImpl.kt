package com.example.mtsinternship.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor() : ApiService {
    // Не нашел API, где на бесплатном тарифе давали бы выбирать валюту, так что запрос относительно EUR.
    // Если купить тариф, тогда стоит добавить в запрос параметр &base=RUB
    // ВАЖНО - apiKey импортируем из файла secrets.kt, находящегося в этом же пакете, в проекте приведен его семпл.
    val url = "http://api.exchangeratesapi.io/v1/latest?access_key=$apiKey"

    // Можно попросить библиотеку парсить полученные данные в один объект data-класса с полями,
    // являющимися курсами валют, но так как не хочется создавать один большой объект и к ответу
    // могут добавиться новые поля(наверное они могут добавить или убрать валюты?)
    // лучше буду парсить JSON в обработчике внучную, чтобы гарантировано отобразить все данные.
    override fun getCurrencies(): Single<JSONObject> {
        return Rx2AndroidNetworking.get(url)
            .build()
            .jsonObjectSingle
    }
}
