package com.cuty.datosconqr.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuty.datosconqr.R
import com.cuty.datosconqr.adapters.PersonAdapter
import com.cuty.datosconqr.connectivity.ConnectivityLiveData
import com.cuty.datosconqr.databinding.FragmentMainBinding
import com.cuty.datosconqr.datasource.LocalDataSource
import com.cuty.datosconqr.domain.AppDatabase
import com.cuty.datosconqr.models.CsvFile
import com.cuty.datosconqr.models.Persona
import com.cuty.datosconqr.savefiles.ExternalFileRepository
import com.cuty.datosconqr.savefiles.FileRepository
import com.cuty.datosconqr.ui.viewmodels.MainViewModel
import com.cuty.datosconqr.ui.viewmodels.VMFactory
import kotlin.Exception

class MainFragment : Fragment(), PersonAdapter.OnItemClickListener {

    private val repo: FileRepository by lazy { ExternalFileRepository(requireActivity().applicationContext) }

    private val viewModel by activityViewModels<MainViewModel> { VMFactory(LocalDataSource(
        AppDatabase.getDatabase(requireActivity().applicationContext))) }
    var connectivityLiveData :  ConnectivityLiveData?=null

    private var binding : FragmentMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setupRecyclerView()
        setupObservers()
        setupNewBu()
        setPermissions()
        setPrintButton()
        setAboutButton()
        connectivityLiveData = ConnectivityLiveData(requireActivity().application)
        observeInternetCheck()
    }
    fun observeInternetCheck(){
        connectivityLiveData?.observe(viewLifecycleOwner, Observer {
                isAvailable->
            when(isAvailable){
                true-> binding?.tvConection?.text= "Connected to internet"
                false->binding?.tvConection?.text = "No conection"
            }
        })
    }
    private fun setAboutButton(){
        binding?.buAbout?.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Programador: Jose Chocobar")
                .setMessage("Deseas contactar al programador de ésta app a traves de whatsapp?")
                .setPositiveButton("Si") { dialog, which ->
                    val i = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://api.whatsapp.com/send?phone=5493875136088&text= Hola, vi tu app y me gustaría trabajar contigo")
                    )
                    startActivity(i)
                }
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .show()
        }
    }
    private fun setAlertDialogForPrint(){
        AlertDialog.Builder(context)
            .setTitle("Generar el archivo de la base de datos")
            .setMessage("Estas seguro que quieres imprimir la base de datos?")
            .setPositiveButton("Si") { dialog, which ->
                printAllData()
                Toast.makeText(context,"Base de datos impresa",Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No", null)
            .setIcon(R.drawable.ic_baseline_print_24)
            .show()
    }
    fun showToast(msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
    private fun printAllData(){
        val fileText = getAllDataToFile() ?:"empty"
        val name = "saveData.csv"
        try {
            repo.addFile(CsvFile(name,fileText))
            shareCsvFile(fileText)
        }catch (e:Exception){
            showToast(e.message.toString())
        }
    }
    private fun shareCsvFile(text:String){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/csv"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
    private fun getAllDataToFile() : String?{
        val list = viewModel.personaItems.value as List<Persona>
        var data : String? = ""
        for (item in list){
            data += "${item.id},${item.primerApellido},${item.primerNombre},${item.diaDeNacimiento}\n"
        }
        return data
    }
    private fun setPrintButton(){
        binding?.buPrintDb?.setOnClickListener {
            setAlertDialogForPrint()
        }
    }

    private fun setPermissions(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.CAMERA),
            //REQUEST_CAMERA_PERMISSION
        201
        )
    }

    private fun setupNewBu() {
        binding?.fab?.setOnClickListener {
            findNavController().navigate(R.id.lectorQr)
        }
    }

    private fun setupObservers() {
        viewModel.personaItems.observe(
            viewLifecycleOwner, {
                result->binding!!.rvMainPersonas.adapter = PersonAdapter(
                requireContext(),
                result as List<Persona>,
                onItemClickListener = this
                )
            }
        )
    }

    private fun setupRecyclerView() {
        binding?.rvMainPersonas?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvMainPersonas?.addItemDecoration(DividerItemDecoration(
            requireContext(),
        DividerItemDecoration.VERTICAL))
    }

    override fun onClick(item: Persona, position: Int) {
        viewModel.deletePerson(item.id)
    }


}