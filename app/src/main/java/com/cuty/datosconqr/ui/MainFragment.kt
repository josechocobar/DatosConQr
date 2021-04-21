package com.cuty.datosconqr.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuty.datosconqr.R
import com.cuty.datosconqr.adapters.PersonAdapter
import com.cuty.datosconqr.databinding.FragmentMainBinding
import com.cuty.datosconqr.datasource.LocalDataSource
import com.cuty.datosconqr.domain.AppDatabase
import com.cuty.datosconqr.models.Persona
import com.cuty.datosconqr.ui.viewmodels.MainViewModel
import com.cuty.datosconqr.ui.viewmodels.VMFactory

class MainFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel> { VMFactory(LocalDataSource(
        AppDatabase.getDatabase(requireActivity().applicationContext))) }

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

    }

    private fun setupNewBu() {
        binding!!.fab.setOnClickListener {
            findNavController().navigate(R.id.lectorQr)
        }
    }

    private fun setupObservers() {
        viewModel.personaItems.observe(
            viewLifecycleOwner, {
                result->binding!!.rvMainPersonas.adapter = PersonAdapter(
                requireContext(),
                result as List<Persona>
                )
            }
        )
    }

    private fun setupRecyclerView() {
        binding!!.rvMainPersonas.layoutManager = LinearLayoutManager(requireContext())
        binding!!.rvMainPersonas.addItemDecoration(DividerItemDecoration(
            requireContext(),
        DividerItemDecoration.VERTICAL))
    }


}