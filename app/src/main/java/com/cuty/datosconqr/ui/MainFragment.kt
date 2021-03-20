package com.cuty.datosconqr.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.cuty.datosconqr.R
import com.cuty.datosconqr.ui.viewmodels.MainViewModel
import com.cuty.datosconqr.ui.viewmodels.VMFactory

class MainFragment : Fragment() {

    //private val viewModel by activityViewModels<MainViewModel> { VMFactory() }

    /*
    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(RepoImplementation(DataSource()), LocarDataSource(AppDatabase.getDatabase(requireActivity().applicationContext)))
    }
     */
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

    }

}