package com.cuty.datosconqr.datasource

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cuty.datosconqr.domain.AppDatabase
import com.cuty.datosconqr.models.Persona
import com.cuty.datosconqr.ui.viewmodels.MainViewModel
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.model.TestClass


class ViewModelTest{

    private lateinit var viewmodel : MainViewModel
    lateinit var persona : Persona


    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java)
            .allowMainThreadQueries().build()
        val datasource = LocalDataSource(db)
        viewmodel = MainViewModel(datasource)
        persona = Persona(1,"jose","chocobar","25/06/1992")

    }

    @Test
    fun testInsert(){
        viewmodel.insert(persona)
        Log.d("test","${persona.id} , ${persona.primerNombre}${persona.primerApellido}")
    }

    @Test
    fun testDelete(){
        viewmodel.insert(persona)
        viewmodel.deletePerson(1)
    }
    @Test
    fun testGet(){
        viewmodel.getperson(1)
    }



}