package com.example.test.screens.virtual_machines

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VirtualMachineListViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VirtualMachineListViewModel::class.java)) {
            return VirtualMachineListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}