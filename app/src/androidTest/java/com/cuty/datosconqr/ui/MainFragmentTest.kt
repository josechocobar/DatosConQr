package com.cuty.datosconqr.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.cuty.datosconqr.R
import org.junit.Before
import org.junit.Test


class MainFragmentTest {
    private lateinit var scenario : FragmentScenario<MainFragment>

    @Before
    fun setup(){
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_DatosConQr) //Theme.DatosConQr
        scenario.moveToState(Lifecycle.State.STARTED)
    }
    @Test
    fun testAboutButtonView(){
        onView(withId(R.id.bu_about)).check(matches(isDisplayed()))
    }
}