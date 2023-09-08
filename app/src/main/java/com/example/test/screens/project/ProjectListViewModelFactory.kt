package com.example.test.screens.project

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Project list view model factory
 *
 * @property application
 * @constructor Create empty Project list view model factory
 */
class ProjectListViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(ProjectListViewModel::class.java)) throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
        return ProjectListViewModel(application) as T
    }

}