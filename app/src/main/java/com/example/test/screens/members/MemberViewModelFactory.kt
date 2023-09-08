package com.example.test.screens.members

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MemberViewModelFactory(private val application: Application, private val id: Int) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(MemberViewModel::class.java)) throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
        return MemberViewModel(application, id) as T
    }

}