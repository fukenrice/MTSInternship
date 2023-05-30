package com.example.mtsinternship.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mtsinternship.RxImmediateSchedulerRule
import com.example.mtsinternship.data.api.ApiService
import com.example.mtsinternship.data.model.CurrencyModel
import io.reactivex.Single
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`


internal class CurrencyListViewModelTest {

    private lateinit var apiService: ApiService
    private lateinit var viewModel: CurrencyListViewModel
    private lateinit var jsonResponse: JSONObject
    private val jsonRaw = "{\n" +
            "  \"success\":true,\n" +
            "  \"timestamp\":1685296863,\n" +
            "  \"base\":\"EUR\",\n" +
            "  \"date\":\"2023-05-28\",\n" +
            "  \"rates\":{\n" +
            "    \"RUB\":10.5,\n" +
            "    \"USD\":1.07,\n" +
            "  }\n" +
            "}"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    @Before
    fun setup() {
        apiService = Mockito.mock(ApiService::class.java)
        viewModel = CurrencyListViewModel(apiService)
        jsonResponse = JSONObject(jsonRaw)
    }

    @Test
    fun getCurrenciesSuccessTest() {
        `when`(apiService.getCurrencies()).thenReturn(Single.just(jsonResponse))
        viewModel.getCurrencies()
        val expectedList = listOf(CurrencyModel("USD", 1.07), CurrencyModel("RUB", 10.5))
        assertEquals(expectedList, viewModel.getCurrencyList().value!!.data)
    }

    @Test
    fun getCurrenciesErrorTest() {
        `when`(apiService.getCurrencies()).thenReturn(
            Single.error(
                Exception("Error msg")
            )
        )
        viewModel.getCurrencies()
        assertEquals("Error msg", viewModel.getCurrencyList().value!!.message)
    }


    @Test
    fun filterListTest() {
        `when`(apiService.getCurrencies()).thenReturn(Single.just(jsonResponse))
        viewModel.getCurrencies()
        viewModel.filterList("U")
        val expectedList = listOf(CurrencyModel("USD", 1.07))
        assertEquals(expectedList, viewModel.getCurrencyList().value!!.data)
    }

    @Test
    fun isEmptyTest() {
        assertTrue(viewModel.isEmpty())
    }

    @Test
    fun isNotEmptyTest() {
        `when`(apiService.getCurrencies()).thenReturn(Single.just(jsonResponse))
        viewModel.getCurrencies()
        assertFalse(viewModel.isEmpty())
    }
}