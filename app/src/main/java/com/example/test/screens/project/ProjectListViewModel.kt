package com.example.test.screens.project

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.database.project.ProjectDatabase
import com.example.test.network.ApiStatus
import com.example.test.repository.ProjectRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Project list view model
 *
 * @constructor
 *
 * @param application
 */
class ProjectListViewModel(application: Application) : ViewModel() {


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    //live data objects
    private val database = ProjectDatabase.getInstance(application.applicationContext)
    private val projectRepository = ProjectRepository(database)
    val projects = projectRepository.projects


    init {
        Timber.i("Calling the API to get all Projects...")

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING


            try {
                projectRepository.refreshProjects()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Timber.e(e)
                Timber.e("Exception occurred while refreshing the projects", e.message)
                _status.value = ApiStatus.ERROR
            }
        }
    }


}