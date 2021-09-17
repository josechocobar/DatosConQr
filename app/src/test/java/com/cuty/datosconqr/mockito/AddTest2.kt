package com.cuty.datosconqr.mockito


import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddTest2  {

    lateinit var validNumber:ValidNumber

    lateinit var add: Add

    lateinit var print: Print

    @Before
    fun setup() {
        validNumber = mockk<ValidNumber>(){
            every { check(and(more(1),less(9))) } returns true
            every{check(1)} returns true
            every { check(9) } returns true
            //every { check(any()) } returns false
        }
        print = mockkClass(Print::class)
        add = Add(validNumber,print)
    }

    @Test
    fun add() {
        val result = add.add(3, 2)
        assertEquals(5,result)
    }
}