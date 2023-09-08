package com.example.test.screens.customers

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.database.customer.CustomerDatabase
import com.example.test.network.ApiStatus
import com.example.test.repository.CustomerRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * List customers view model
 *
 * @constructor
 *
 * @param application
 */
class ListCustomersViewModel(application: Application) : ViewModel() {

    //live data objects
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = CustomerDatabase.getInstance(application.applicationContext)
    private val customerRepository = CustomerRepository(database)
    val customers = customerRepository.customers

    init {
        Timber.i("Refreshing the customers")

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING


            try {
                customerRepository.refreshCustomers()
                Timber.tag("data").i("log customers$customers")
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Timber.e("Exception occurred while refreshing the customers", e.message)
                _status.value = ApiStatus.ERROR
            }
        }
    }

}