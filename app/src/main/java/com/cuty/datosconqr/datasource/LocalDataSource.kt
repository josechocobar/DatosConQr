package com.cuty.datosconqr.datasource

import androidx.annotation.WorkerThread
import com.cuty.datosconqr.domain.AppDatabase
import com.cuty.datosconqr.models.Persona
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val appDatabase: AppDatabase) {

    val allPersonById: Flow<List<Persona>> = appDatabase.personaDao().getAllByDni()


    val allPersonByApellido: Flow<List<Persona>> = appDatabase.personaDao().getAllByApellido()


    fun getPersona(numeroDni:Int): Persona{
        return appDatabase.personaDao().getByDni(numeroDni)
    }

    fun getPersona(apellido:String) :Persona {
        return appDatabase.personaDao().getByApellido(apellido)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(persona: Persona) {
        appDatabase.personaDao().insertItem(persona)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteItem(id: Int) {
        appDatabase.personaDao().deleteItemByDni(id)
    }


}