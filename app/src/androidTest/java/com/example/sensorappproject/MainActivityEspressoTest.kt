package com.example.sensorappproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sensorappproject.R
import com.example.sensorappproject.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Espresso tests for main activity
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    // Test to check the navigation to sensors fragment and if the dropdown menu is available
    @Test
    fun testNavigationToSensorsFragment() {
        onView(withId(R.id.navigation_sensors)).perform(click())
        onView(withId(R.id.sensor_spinner)).check(matches(isDisplayed()))
    }

    // Test to check if the navigation to the data fragment and correct title display is possible
    @Test
    fun testNavigationToDataFragment() {
        onView(withId(R.id.navigation_data)).perform(click())
        onView(withId(R.id.data_title)).check(matches(isDisplayed()))
    }

    // Test to check if the start button in sensor fragment is functioning
    @Test
    fun testStartButtonFunctionality() {
        onView(withId(R.id.navigation_sensors)).perform(click())
        onView(withId(R.id.startButton)).perform(click())
        onView(withId(R.id.liveSensorValues)).check(matches(isDisplayed()))
    }
}
