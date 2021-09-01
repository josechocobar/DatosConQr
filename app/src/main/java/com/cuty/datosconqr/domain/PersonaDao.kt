package com.cuty.datosconqr.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cuty.datosconqr.models.Persona
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {
    @Query("SELECT * FROM tabla_persona ORDER BY nro_dni")
    fun getAllByDni(): Flow<List<Persona>>

    @Query("SELECT * FROM tabla_persona ORDER BY primer_apellido" )
    fun getAllByApellido():Flow<List<Persona>>

    @Query("SELECT * FROM tabla_persona WHERE nro_dni =:numeroDni")
    fun getByDni(numeroDni:Int):Flow<List<Persona>>

    @Query("SELECT * FROM tabla_persona WHERE primer_apellido =:apellido")
    fun getByApellido(apellido:String): Flow<List<Persona>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(persona: Persona)

    @Query("DELETE FROM tabla_persona WHERE nro_dni =:dni")
    suspend fun deleteItemByDni(dni:Int)

    /*
    @Query("SELECT * FROM user WHERE age > :minAge")
        fun loadAllUsersOlderThan(minAge: Int): Array<User>
     */
}