package com.cuty.datosconqr.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cuty.datosconqr.datasource.LocalDataSource
import java.lang.IllegalArgumentException

class VMFactory (private val localDataSource: LocalDataSource) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(localDataSource) as T
        }
        throw IllegalArgumentException("Uknown ViewModel Class")
        //return modelClass.getConstructor(RepoImplementation::class.java).newInstance(repo)
    }
}
