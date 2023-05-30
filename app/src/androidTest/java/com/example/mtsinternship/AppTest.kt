package com.example.mtsinternship

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.mtsinternship.ui.main.MainActivity
import org.junit.Test


class AppTest {

    @Test
    fun runApp() {
        ActivityScenario.launch(MainActivity::class.java)

        // Фильтрация
        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("RU"), closeSoftKeyboard())
        onView(withText("RUB"))
            .check(matches(isDisplayed()))

        // Переход на экран конвертации
        onView(withText("RUB")).perform(click())

        // Ввод текста в поле
        onView(withId(R.id.etBaseCurrency)).perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.etConvertedCurrencyAmount)).check(matches(withText("1000.0")))

    }
}
