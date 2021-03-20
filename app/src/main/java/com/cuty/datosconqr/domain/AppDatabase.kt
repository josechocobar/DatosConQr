package com.cuty.datosconqr.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cuty.datosconqr.models.Persona

@Database(entities = [Persona::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tabla_persona"
                ).build()
                INSTANCE = instance
                return INSTANCE!!
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}