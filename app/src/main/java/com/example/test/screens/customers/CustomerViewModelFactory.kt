package com.example.test.screens.customers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomerViewModelFactory(private val application: Application, private val id: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(CustomerViewModel::class.java)) throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
        return CustomerViewModel(application, id) as T
    }
}
