package com.example.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTest {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    private val price = "100.00"
    private val customPrice = "13"

    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(price))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("20,00"))))
    }

    @Test
    fun calculate_20_percent_tip_no_round_up() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(customPrice))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.round_up_switch)).perform(click())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("2,60"))))
    }

    @Test
    fun calculate_15_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(price))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_fifteen_percent)).perform(click())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("15,00"))))
    }

    @Test
    fun calculate_15_percent_tip_no_round_up() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(customPrice))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_fifteen_percent)).perform(click())

        onView(withId(R.id.round_up_switch)).perform(click())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("1,95"))))
    }

    @Test
    fun calculate_10_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(price))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_ten_percent)).perform(click())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("10,00"))))
    }

    @Test
    fun calculate_10_percent_tip_no_round_up() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(customPrice))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_ten_percent)).perform(click())

        onView(withId(R.id.round_up_switch)).perform(click())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("1,30"))))
    }

    @Test
    fun calculate_custom_25_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(price))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_custom)).perform(click())

        onView(withId(R.id.custom_price_edit_text)).perform(typeText("25"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("25,00"))))
    }

    @Test
    fun calculate_25_percent_tip_no_round_up() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText(customPrice))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_custom)).perform(click())

        onView(withId(R.id.custom_price_edit_text)).perform(typeText("25"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.round_up_switch)).perform(click())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("3,25"))))
    }
}