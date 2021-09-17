package com.cuty.datosconqr.mockito

import io.mockk.*
import org.junit.Assert.*
import org.junit.jupiter.api.*

import org.junit.Before
import org.junit.Test


class AddTest {

    lateinit var validNumber: ValidNumber

    lateinit var add: Add
    lateinit var print: Print

    @Before
    fun setup() {
        validNumber = mockk<ValidNumber>() {
            every { check(and(more(0), less(9)))} returns true
            every { check(less(1)) } returns false
            every { check(more(8)) } returns false
            every { checkZero(0) } throws ArithmeticException()
            every { doubleToInt(9.9999) } returns 9
            every { doubleToInt("9.9999") } returns 0
        }

        print = mockkClass(Print::class){
            every { showMessage(any()) } just Runs
        }

        add = Add(validNumber,print)
    }

    @Test
    fun add_ambos_pasan() {
        assertEquals(5, add.add(2, 3))
    }

    @Test
    fun add_solo_uno_pasa() {
        assertEquals(0, add.add(0, 2))
    }

    @Test
    fun add_ninguno_pasa() {
        assertEquals(0, add.add(0, 9))
    }
    @Test
    fun check_zero_test(){
        Assertions.assertThrows(ArithmeticException::class.java){
            validNumber.checkZero(0)
        }
    }
    @Test
    fun doubleToIntTest(){
        assertEquals(9, validNumber.doubleToInt(9.9999))
    }
    @Test
    fun doubleToIntTest2(){
        assertEquals(0,validNumber.doubleToInt("9.9999"))
    }
    @Test
    fun addPrintTest(){
        val result = add.add(4,4)
        assertEquals(8,result)
        add.addPrint(4,4)

        verify { print.showMessage(8) }

    }
    /*
    patrones
    AAA
    arrange -> cuando ponemos el comportamiento que esperamos del mock
    action -> cuando ponemos la accion/funcion a testear
    assert -> cuando hacemos el assert del valor esperado y el valor que arroja la funcion


     */

}