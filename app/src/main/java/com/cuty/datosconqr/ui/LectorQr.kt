package com.cuty.datosconqr.ui

import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.cuty.datosconqr.R
import com.cuty.datosconqr.databinding.FragmentLectorQrBinding
import com.cuty.datosconqr.datasource.LocalDataSource
import com.cuty.datosconqr.domain.AppDatabase
import com.cuty.datosconqr.models.Persona
import com.cuty.datosconqr.ui.viewmodels.MainViewModel
import com.cuty.datosconqr.ui.viewmodels.VMFactory
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException
import java.lang.Exception


class LectorQr : Fragment() {

    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            LocalDataSource(
                AppDatabase.getDatabase(requireActivity().applicationContext)
            )
        )
    }

    private var surfaceView: SurfaceView? = null
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null
    private val REQUEST_CAMERA_PERMISSION = 201

    //This class provides methods to play DTMF tones
    private var toneGen1: ToneGenerator? = null
    private var barcodeText: TextView? = null
    private var barcodeData: String? = null
    private var nameText: TextView? = null
    private var surnameText: TextView? = null
    private var birthdayText: TextView? = null
    private var saveDataButton: FloatingActionButton? = null

    private var binding: FragmentLectorQrBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lector_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLectorQrBinding.bind(view)
        toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        surfaceView = binding?.surfaceView
        barcodeText = binding?.idNumberLectorQr
        nameText = binding?.nameLectorQr
        surnameText = binding?.surnameLectorQr
        birthdayText = binding?.birthdayLectorQr
        saveDataButton = binding?.buSaveData



        initialiseDetectorsAndSources()
        inicialiseSaveButton()
    }

    private fun inicialiseSaveButton() {
        saveDataButton?.setOnClickListener {
            try {
                if (
                    barcodeText?.text != "" &&
                    nameText?.text != "" &&
                    surnameText?.text != "" &&
                    birthdayText?.text != ""
                ) {
                    viewModel.insert(
                        Persona(
                            barcodeText?.text.toString().toInt(),
                            nameText?.text.toString(),
                            surnameText?.text.toString(),
                            birthdayText?.text.toString()
                        )
                    )
                }else{
                    Toast.makeText(
                        context,
                        "Alguno de los campos esta vacio",
                        Toast.LENGTH_LONG
                    ).show()
                }
                barcodeText?.text = ""
                nameText?.text = ""
                surnameText?.text = ""
                birthdayText?.text = ""
                Toast.makeText(
                    context,
                    "La persona se guardó correctamente en la base de datos",
                    Toast.LENGTH_LONG
                ).show()


            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "No se pudo guardar porque: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initialiseDetectorsAndSources() {


        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = BarcodeDetector.Builder(context)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(context, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()
        surfaceView?.holder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            android.Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(surfaceView!!.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(android.Manifest.permission.CAMERA),
                            REQUEST_CAMERA_PERMISSION
                        )
                        if (ActivityCompat.checkSelfPermission(
                                requireContext(),
                                android.Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            cameraSource?.start(surfaceView!!.holder)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })
        barcodeDetector!!.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(
                    requireContext(),
                    "To prevent memory leaks barcode scanner has been stopped",
                    Toast.LENGTH_SHORT
                ).show();
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    barcodeText!!.post {
                        if (barcodes.valueAt(0).email != null) {
                            Toast.makeText(requireContext(), "if", Toast.LENGTH_SHORT).show()
                            barcodeText!!.removeCallbacks(null)
                            barcodeData = barcodes.valueAt(0).email.address


                        } else {
                            //Toast.makeText(requireContext(), "else", Toast.LENGTH_SHORT).show()
                            barcodeData = barcodes.valueAt(0).displayValue
                            val data = barcodeData?.split("@")
                            val persona: Persona? = data?.let {
                                Persona(
                                    data[4].toInt(), data[2],
                                    data[1], data[6]
                                )

                            }
                            persona?.let {
                                barcodeText?.text = persona.id.toString()
                                nameText?.text = persona.primerNombre
                                surnameText?.text = persona.primerApellido
                                birthdayText?.text = persona.diaDeNacimiento

                                toneGen1?.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                            }
                            //barcodeText!!.text = barcodeData
                            toneGen1?.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                        }
                    }
                }
            }
        })
    }
}