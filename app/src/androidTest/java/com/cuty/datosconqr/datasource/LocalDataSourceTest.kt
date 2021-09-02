package com.cuty.datosconqr.datasource

import android.content.Context
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


class LocalDataSourceTest : TestCase(){
    private lateinit var viewmodel : MainViewModel

    @Before
    override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java)
            .allowMainThreadQueries().build()
        val datasource = LocalDataSource(db)
        viewmodel = MainViewModel(datasource)

    }
    @Test
    fun testMainViewModel(){
        viewmodel.insert(Persona(1,"jose","chocobar","25/06/1992"))
        val result = viewmodel.getperson(1)
        assertEquals(result,Persona(1,"jose","chocobar","25/06/1992"))
    }

}