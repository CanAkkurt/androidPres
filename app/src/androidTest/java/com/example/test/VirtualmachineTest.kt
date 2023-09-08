package com.example.test

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
@LargeTest
class VirtualmachineTest {


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // Perform any setup here, if needed
    }

    @Test
    fun testSearchView() {


        sleep(15000)


// Choose item "Settings"
        // open navigation drawer by clicking on 3 line icon in top left corner

//        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout)).perform(ViewActions.)

        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout)).perform(ViewActions.swipeRight())
        //


// Check that settings activity was opened
//        onView(withText(mActivity.getString(R.string.some_settings_item))).check(matches(isDisplayed()));


//        onView(withId(R.id.search_view)).perform(click())
//        val scenario = launchFragmentInContainer<VirtualMachineListFragment>()


        //scroll to search view
        // go to virtual machine list fragment and scroll to search view

//
//        Espresso.onView(ViewMatchers.withId(R.id.search_view)).perform(ViewActions.typeText("Vm One"))
//        Espresso.onView(ViewMatchers.withId(R.id.search_view)).perform(ViewActions.closeSoftKeyboard())
//
//
//        // check if list contains only one item
//        // check if item contains "Vm One"
//        Espresso.onView(ViewMatchers.withId(R.id.virtual_machine_list)) // Replace with your filtered list view ID
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())) // Ensure the filtered list is visible
//            .check(ViewAssertions.matches(ViewMatchers.hasChildCount(1))) // Check if the filtered list has one child
//
//        // Optionally, you can further assert the content of the filtered list item
//        // For example, check if it contains the expected text "Vm One"
//        Espresso.onView(ViewMatchers.withText("Vm One"))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        //check if u are now in another activity

        // Find the button and perform a click action
    }


    @Test
    fun testSearchView2() {
        val chipGroupMatcher = ViewMatchers.withId(R.id.chip_group)

        Thread.sleep(10000)
        //scroll to search view
        Espresso.onView(ViewMatchers.withId(R.id.chart)).perform(ViewActions.swipeUp())
//        Espresso.onView(ViewMatchers.withId(R.id.chip_group.)).perform(ViewActions.click())
        //chipGroup click on first chip
        Espresso.onView(ViewMatchers.withText("Requested")).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.search_view))
            .perform(ViewActions.typeText("Vm One"))
        Espresso.onView(ViewMatchers.withId(R.id.search_view))
            .perform(ViewActions.closeSoftKeyboard())
    }


    @Test
    fun testSearchView3() {
        val chipGroupMatcher = ViewMatchers.withId(R.id.chip_group)

        Thread.sleep(10000)
        //scroll to search view
        Espresso.onView(ViewMatchers.withId(R.id.chart)).perform(ViewActions.swipeUp())
//        Espresso.onView(ViewMatchers.withId(R.id.chip_group.)).perform(ViewActions.click())
        //chipGroup click on first chip
        Espresso.onView(ViewMatchers.withText("Denied")).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.search_view))
            .perform(ViewActions.typeText("VM Five"))
        Espresso.onView(ViewMatchers.withId(R.id.search_view))
            .perform(ViewActions.closeSoftKeyboard())
    }


}