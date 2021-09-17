package com.cuty.datosconqr.savefiles

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cuty.datosconqr.models.CsvFile
import org.junit.Assert.*
import org.junit.Before
import java.io.FileOutputStream

class ExternalFileRepositoryTest{
    lateinit var externalFileRepository: ExternalFileRepository

    lateinit var context: Context

    lateinit var fileOutput : FileOutputStream

    lateinit var csv : CsvFile

    @Before
    fun setup(){
        context = ApplicationProvider.getApplicationContext<Context>()
    }
}

/*
class ExternalFileRepositoryTest {
    /*
    @InjectMocks
    var externalFileRepository: ExternalFileRepository? = null

    @Mock
    var context: Context? = mock(Context::class.java)

    var mockFos: FileOutputStream = mock(FileOutputStream::class.java)


    @Mock
    var csv: CsvFile? = null


    @Before
    fun setup() {
        //context = ApplicationProvider.getApplicationContext<Context>()
        MockitoAnnotations.initMocks(this)
    }

    /*
    @Test
    fun addFileTest() {
        externalFileRepository!!.addFile(csv!!)

    }

     */
    @Test
    fun isExternalReadableTest() {
        externalFileRepository?.isExternalStorageReadable()?.let { assertTrue("is readable", it) }

    }

     */
}
 */