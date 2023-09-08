package com.example.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.test.database.VirutalMachine.VirtualMachineDatabase
import com.example.test.database.VirutalMachine.asDomainModel
import com.example.test.domain.VirtualMachine
import com.example.test.network.asDatabaseVirtualMachine
import com.example.test.network.interfaces.ApiVirtualMachineObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class VirtualMachineRepository(private val database: VirtualMachineDatabase) {
    // LiveData object with Virtual Machines from the database transformed to domain Virtual Machines
    val virtualmachines: LiveData<List<VirtualMachine>> =
        Transformations.map(database.virtualMachineDatabaseDao.getAllVirtualMachinesLive()) {
            it.asDomainModel()
        }

    // Network call to refresh the Virtual Machines
    suspend fun refreshVirtualMachines() {
        // Switch the context to an IO thread
        withContext(Dispatchers.IO) {
            val apiVirtualMachine =
                ApiVirtualMachineObj.retrofitService.getVirtualMachines().await()
            // '*': Kotlin spread operator, used for functions that expect a vararg param and this just spreads the array into separate fields
            database.virtualMachineDatabaseDao.insertAll(*apiVirtualMachine.virtualMachines.asDatabaseVirtualMachine())
            Timber.i("Refreshed customers from the API and saved them in the database")
        }
    }

}
