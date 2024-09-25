package com.example.assignment2rentwithintent


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest2 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    //here we test to see if pressing the next button works by clicking and seeing if the itemName changes
    fun testNextButton() {
        onView(withId(R.id.Next)).perform(click())
        onView(withId(R.id.itemName)).check(matches(not(withText("Guitar"))))
    }
    @Test
    //here we do the same but for the previous button and check that the first item is Guitar
    fun testPreviousButton() {
        onView(withId(R.id.Previous)).perform(click())
        onView(withId(R.id.itemName)).check(matches(not(withText("Guitar"))))
    }
    @Test
    //now we are testing to see if clicking on the image opens up the details page
    fun testDetailsActivityClick() {
        onView(withId(R.id.itemImg)).perform(click())
        onView(withId(R.id.detailsName)).check(matches(isDisplayed()))
    }

    @Test
    //do the same but to see if the second activity starts when borrow btn is clicked and check if data has passed by checking the name of the item is parsed
    fun testSecondActivityClick() {

        onView(withId(R.id.borrow)).perform(click())
        onView(withId(R.id.name)).check(matches(isDisplayed()))
    }

}
