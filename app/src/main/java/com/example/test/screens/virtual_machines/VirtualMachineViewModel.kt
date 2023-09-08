package com.example.test.screens.virtual_machines

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.database.VirutalMachine.VirtualMachineDatabase
import com.example.test.database.VirutalMachine.asDomainModel
import com.example.test.domain.VirtualMachine
import com.example.test.network.ApiStatus
import com.example.test.repository.VirtualMachineRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Virtual machine view model
 *
 * @property id
 * @constructor
 *
 * @param application
 */
class VirtualMachineViewModel(application: Application, val id: Int) :
    AndroidViewModel(application) {

    //live data objects
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = VirtualMachineDatabase.getInstance(application.applicationContext)
    private val virtualMachineRepository = VirtualMachineRepository(database)

    val customers = virtualMachineRepository.virtualmachines
    private val _virtualMachine = MutableLiveData<VirtualMachine>()
    val virtualMachine: LiveData<VirtualMachine>
        get() = _virtualMachine


    init {
        Timber.i("Getting VirtualMachine $id")

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING

            try {
                val temp = database.virtualMachineDatabaseDao.getVirtualMachineById(id)
                    ?: throw Resources.NotFoundException("VirtualMachine $id was not found.")
                _virtualMachine.value = temp.asDomainModel()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {

                Timber.e("Exception occurred while refreshing the virtualMachines" + e.message, e)
                Timber.i(e)
                _status.value = ApiStatus.ERROR
            }
        }
    }

}