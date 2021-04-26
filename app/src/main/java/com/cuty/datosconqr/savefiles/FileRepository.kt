package com.cuty.datosconqr.savefiles

import com.cuty.datosconqr.models.CsvFile

interface FileRepository {
    fun addFile(csvFile: CsvFile)
    fun getFile(fileName: String): CsvFile
    fun deleteFile(fileName: String): Boolean
}