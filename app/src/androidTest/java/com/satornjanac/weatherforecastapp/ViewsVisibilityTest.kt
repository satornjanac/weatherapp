package com.satornjanac.weatherforecastapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.satornjanac.weatherforcastapp.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ViewsVisibilityTest {

    @JvmField
    @Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @JvmField
    @Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testUI() {
        Thread.sleep(1000)
        onView(withId(R.id.sectionsList)).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.title), withText("TestCurrentSection"),
                withParent(
                    allOf(
                        withId(R.id.currentWeather),
                        withParent(withId(R.id.card_view))
                    )
                ),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.weatherCode), withText("Overcast"),
                withParent(
                    allOf(
                        withId(R.id.currentWeather),
                        withParent(withId(R.id.card_view))
                    )
                ),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.currentTemperature),
                withParent(
                    allOf(
                        withId(R.id.currentWeather),
                        withParent(withId(R.id.card_view))
                    )
                ),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.currentWeatherCodeIcon),
                withParent(
                    allOf(
                        withId(R.id.currentWeather),
                        withParent(withId(R.id.card_view))
                    )
                ),
                isDisplayed()
            )
        ).isGone()

    }

}

fun ViewInteraction.isGone() = ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE))