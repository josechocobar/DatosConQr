package com.cuty.datosconqr.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.*
import com.cuty.datosconqr.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LectorQrTest{
    private lateinit var scenario: FragmentScenario<LectorQr>

    @Before
    fun setup(){
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_DatosConQr)
        scenario.moveToState(Lifecycle.State.STARTED)
    }
    @Test
    fun testAddPersona(){
        val primerNombre = "Lisandro"
        val apellido = "Canueto"
        val id = "36856242"
        val birthday = "21011992"
        onView(withId(R.id.name_lector_qr)).perform(typeText(primerNombre))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.surname_lector_qr)).perform(typeText(apellido))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.id_number_lector_qr)).perform(typeText(id))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.birthday_lector_qr)).perform(typeText(birthday))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.bu_save_data)).perform(click())
        onView(withId(R.id.name_lector_qr)).check(matches(withText("")))
    }

}