package com.example.test.screens.members

import android.app.Application
import android.content.res.Resources.NotFoundException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.database.VirutalMachine.VirtualMachineDatabase
import com.example.test.database.member.MemberDatabase
import com.example.test.database.member.asDomainModel
import com.example.test.domain.Member
import com.example.test.domain.VirtualMachine
import com.example.test.network.ApiStatus
import com.example.test.repository.VirtualMachineRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Member view model
 *
 * @constructor
 *
 * @param application
 * @param id
 */
class MemberViewModel(application: Application, id: Int) : AndroidViewModel(application) {

    //live data objects
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = MemberDatabase.getInstance(application.applicationContext)
    private val databaseVirtualMachine =
        VirtualMachineDatabase.getInstance(application.applicationContext)

    private val _member = MutableLiveData<Member>()
    val member: LiveData<Member>
        get() = _member
    private val _virtualmachine = MutableLiveData<VirtualMachine>()

    private val virtualMachineRepository = VirtualMachineRepository(databaseVirtualMachine)

    val virtualMachineList = virtualMachineRepository.virtualmachines


    private val _backupContact = MutableLiveData<Member>()
    val backupContact: LiveData<Member>
        get() = _backupContact


    init {
        Timber.i("Getting Member $id")

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING

            try {
                val tempMember = database.memberDatabaseDao.getMemberById(id)
                    ?: throw NotFoundException("Member $id was not found.")
                _member.value = tempMember.asDomainModel()

                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Timber.e("Exception occurred while refreshing the customers", e)
                _status.value = ApiStatus.ERROR
            }
        }
    }

}