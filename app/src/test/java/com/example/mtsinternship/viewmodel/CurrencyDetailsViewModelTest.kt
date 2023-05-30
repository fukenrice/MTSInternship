package com.example.mtsinternship.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class CurrencyDetailsViewModelTest {

    private lateinit var viewModel: CurrencyDetailsViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = CurrencyDetailsViewModel()
    }

    @Test
    fun convertValue() {
        viewModel.exchangeRate = 3.0f
        val expectedValue = 9.0
        viewModel.convertValue(3.0)
        assertEquals(expectedValue, viewModel.convertedValue.value)
    }
}
