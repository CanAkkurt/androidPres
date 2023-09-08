package com.example.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.test.screens.login.LoginActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        // Perform any setup here, if needed
    }

    @Test
    fun testButtonClickLogin() {

        onView(withId(R.id.loginButton)).perform(click())


        //check if u are now in another activity

        // Find the button and perform a click action

    }

}
