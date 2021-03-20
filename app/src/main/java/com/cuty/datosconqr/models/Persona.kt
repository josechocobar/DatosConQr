package com.cuty.datosconqr.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Entity (tableName = "tabla_persona")
@Parcelize
data class Persona(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "nro_dni")
    var id: Int,
    @ColumnInfo(name = "primer_nombre")
    var primerNombre: String,
    @ColumnInfo(name = "segundo_nombre")
    var segundoNombre: String? = null,
    @ColumnInfo(name = "tercer_nombre")
    var tercerNombre: String? = null,
    @ColumnInfo(name = "primer_apellido")
    var primerApellido: String,
    @ColumnInfo(name = "segundo_apellido")
    var segundoApellido: String? = null,
    @ColumnInfo(name = "estado_civil")
    var estadoCivil : Boolean, // false soltero, true casado
    @ColumnInfo(name = "dia_nacimiento")
    var diaDeNacimiento : LocalDate,
    @ColumnInfo(name = "dia_fallecimiento")
    var diaDeFallecimiento : LocalDate,
    @ColumnInfo(name = "aviso_fallecido")
    var avisoDeFallecido : Boolean
) : Parcelable
