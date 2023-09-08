package com.example.test.screens.members

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.database.member.MemberDatabase
import com.example.test.network.ApiStatus
import com.example.test.repository.MemberRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Member list view model
 *
 * @constructor
 *
 * @param application
 */
class MemberListViewModel(application: Application) : ViewModel() {
    //live data objects
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = MemberDatabase.getInstance(application.applicationContext)
    private val memberRepository = MemberRepository(database)

    val listMembers = memberRepository.members

    init {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING

            try {
                memberRepository.refreshMembers()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Timber.e("Exception occurred while refreshing the customers", e)
                _status.value = ApiStatus.ERROR
            }
        }
    }

}