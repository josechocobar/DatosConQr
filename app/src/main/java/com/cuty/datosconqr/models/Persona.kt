package com.cuty.datosconqr.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "tabla_persona")
@Parcelize
data class Persona(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "nro_dni")
    var id: String,
    @ColumnInfo(name = "primer_nombre")
    var primerNombre: String,
    @ColumnInfo(name = "primer_apellido")
    var primerApellido: String,
    @ColumnInfo(name = "dia_nacimiento")
    var diaDeNacimiento: String,
) : Parcelable
