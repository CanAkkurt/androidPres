package com.example.test.screens.virtual_machines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.database.VirutalMachine.VirtualMachineDatabase
import com.example.test.network.ApiStatus
import com.example.test.repository.VirtualMachineRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Virtual machine list view model
 *
 * @constructor
 *
 * @param application
 */
class VirtualMachineListViewModel(application: Application) : AndroidViewModel(application) {

    //live data objects
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = VirtualMachineDatabase.getInstance(application.applicationContext)
    private val virtualMachineRepository = VirtualMachineRepository(database)

    val virtualMachineList = virtualMachineRepository.virtualmachines

    init {
        Timber.i("Refreshing the VMs")

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING

            try {
                virtualMachineRepository.refreshVirtualMachines()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Timber.i(e)
                Timber.e("Exception occurred while refreshing the virtualmachines", e)
                _status.value = ApiStatus.ERROR
            }
        }
    }


}