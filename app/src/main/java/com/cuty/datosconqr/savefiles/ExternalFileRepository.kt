package com.cuty.datosconqr.savefiles

import android.content.Context
import android.os.Environment
import android.util.Log
import com.cuty.datosconqr.models.CsvFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ExternalFileRepository(var context: Context) : FileRepository {
    override fun addFile(csvFile: CsvFile) {
        if (isExternalStorageWritable()) {
            FileOutputStream(csvFile(csvFile.fileName)).use { output ->
                output.write(csvFile.fileText.toByteArray())
            }
        }
    }

    override fun getFile(fileName: String): CsvFile {
        val note = CsvFile(fileName, "")
        if (isExternalStorageReadable()) {
            FileInputStream(csvFile(fileName)).use { stream ->
                val text = stream.bufferedReader().use {
                    it.readText()
                }
                note.fileText = text
            }
        }
        return note
    }

    override fun deleteFile(fileName: String): Boolean {
        return isExternalStorageWritable() && csvFile(fileName).delete()
    }

    private fun noteDirectory(): File? = context.getExternalFilesDir(null)

    private fun csvFile(fileName: String): File = File(noteDirectory(), fileName)

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
}