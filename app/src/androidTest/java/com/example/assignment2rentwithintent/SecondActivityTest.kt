package com.example.assignment2rentwithintent

import android.content.Intent
import android.view.View
import android.widget.SeekBar
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isSystemAlertWindow
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SecondActivityTest {

    private lateinit var testItem: MusicalEquipment
    private var totalCredits = 500

    @Test
    fun testDetailsPass() {
        testItem=MusicalEquipment("test guitar", false,3.5f,250,R.drawable.guitar,R.drawable.bookedgtar,"test of the guitar",true)
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), SecondActivity::class.java).apply {
                putExtra("Instrument", testItem)
                putExtra("totalCredits", totalCredits)
            }
        ActivityScenario.launch<SecondActivity>(intent)

        onView(withId(R.id.borrowImg)).check(matches(isDisplayed()))
        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
    }

    @Test
    fun testInsufficientDetails() {
        testItem=MusicalEquipment("test guitar", false,3.5f,250,R.drawable.guitar,R.drawable.bookedgtar,"test of the guitar",true)
        val intent = Intent(ApplicationProvider.getApplicationContext(), SecondActivity::class.java).apply {
            putExtra("Instrument", testItem)
            putExtra("totalCredits", 100)
        }
        ActivityScenario.launch<SecondActivity>(intent)

        onView(withId(R.id.borrowCredit)).check(matches(withText("Credits: 100")))
        onView(withId(R.id.termsCheck)).perform(click())
        onView(withId(R.id.privacyCheck)).perform(click())
        onView(withId(R.id.save)).perform(click())
        onView(withId(R.id.recharge)).check(matches(isDisplayed()))
        onView(withId(R.id.rechargeY)).check(matches(isDisplayed()))
        onView(withId(R.id.rechargeN)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecharge() {
        testItem=MusicalEquipment("test guitar", false,3.5f,250,R.drawable.guitar,R.drawable.bookedgtar,"test of the guitar",true)
        val intent = Intent(ApplicationProvider.getApplicationContext(), SecondActivity::class.java).apply {
            putExtra("Instrument", testItem)
            putExtra("totalCredits", 100)
        }
        ActivityScenario.launch<SecondActivity>(intent)

        onView(withId(R.id.save)).perform(click())
        onView(withId(R.id.rechargeY)).perform(click())
        onView(withId(R.id.borrowCredit)).check(matches(withText("Credits: 500")))
        onView(withId(R.id.recharge)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rechargeY)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rechargeN)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testSeekBarUpdatesRentDuration() {
        val testInstrument = MusicalEquipment("Test Instrument", true, 4.5f, 250, R.drawable.guitar, R.drawable.bookedgtar, "Test description", true)
        val intent = Intent(ApplicationProvider.getApplicationContext(), SecondActivity::class.java).apply {
            putExtra("Instrument", testInstrument)
            putExtra("totalCredits", 500)
        }
        ActivityScenario.launch<SecondActivity>(intent)


        onView(withId(R.id.rentTime)).check(matches(withText("1 month")))

        val rentDurations = listOf("1 month", "3 months", "6 months", "9 months", "12 months")
        for (i in rentDurations.indices) {
            onView(withId(R.id.rent)).perform(setProgress(i))
            onView(withId(R.id.rentTime)).check(matches(withText(rentDurations[i])))
        }
    }

    // Helper function to set SeekBar progress
    private fun setProgress(progress: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints() = isAssignableFrom(SeekBar::class.java)
            override fun getDescription() = "Set SeekBar progress to $progress"
            override fun perform(uiController: UiController, view: View) {
                (view as SeekBar).progress = progress
                uiController.loopMainThreadUntilIdle()
            }
        }
    }



}