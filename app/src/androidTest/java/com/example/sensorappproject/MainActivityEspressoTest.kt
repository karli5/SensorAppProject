package com.example.sensorappproject
/*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sensorappproject.R
import com.example.sensorappproject.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testBottomNavigation() {
        // Home -> Sensors
        onView(withId(R.id.navigation_sensors)).perform(click())

        // Sensors -> Data
        onView(withId(R.id.navigation_data)).perform(click())

        // Data -> Home
        onView(withId(R.id.navigation_home)).perform(click())
    }

    @Test
    fun testSensorListAndBackButton() {
        // Home -> Sensors
        onView(withId(R.id.navigation_sensors)).perform(click())

        // Klicken Sie auf ein Element in der Liste
        //onView(withId(R.id.sensor_recycler_view)).perform(click())

        // Klicken Sie auf die Schaltfläche Zurück
        onView(withId(android.R.id.home)).perform(click())
    }
}
*/