package com.example.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.test.database.project.ProjectDatabase
import com.example.test.database.project.asDomainModel
import com.example.test.domain.Project
import com.example.test.network.asDatabaseProject
import com.example.test.network.interfaces.ApiProjectObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProjectRepository(private val database: ProjectDatabase) {

    // LiveData object with Actors from the database transformed to domain Customers

    val projects: LiveData<List<Project>> =
        Transformations.map(database.ProjectDatabaseDao.getAllProjectsLive()) {
            it.asDomainModel()
        }


    // Network call to refresh the Customers
    suspend fun refreshProjects() {
        // Switch the context to an IO thread

        withContext(Dispatchers.IO) {
            val apiProject = ApiProjectObj.retrofitService.getProjects().await()
//            Timber.i(ApiCustomerObj.retrofitService.getCustomerById("1").toString())
//                val apiProjectContainer = ApiProjectContainer(apiProject.apiProjects)
            // '*': Kotlin spread operator, used for functions that expect a vararg param and this just spreads the array into separate fields
            database.ProjectDatabaseDao.insertAll(*apiProject.projects.asDatabaseProject())
//            database.customerDatabaseDao.insertAll(*apiCustomer.asDatabaseCustomer())
            Timber.i("Refreshed Projects from the API and saved them in the database")
        }
    }
}

