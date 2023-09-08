package com.example.test.screens.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.Auth0
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.test.R
import com.example.test.databinding.FragmentLoginBinding
import com.example.test.screens.virtual_machines.VirtualMachineListViewModel
import com.example.test.screens.virtual_machines.VirtualMachineListViewModelFactory

/**
 * Login fragment
 *
 * @constructor Create empty Login fragment
 */
class LoginFragment : Fragment() {


    private var cachedCredentials: Credentials? = null
    private var cachedUserProfile: UserProfile? = null
    private lateinit var binding: FragmentLoginBinding
    private var prefs: SharedPreferences? = null
    private lateinit var account: Auth0


    //virtualmachine viewmodel
    private val vmViewModel: VirtualMachineListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, VirtualMachineListViewModelFactory(activity.application)).get(
            VirtualMachineListViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        prefs = activity?.getSharedPreferences("com.example.test", Context.MODE_PRIVATE)
        return binding.root
    }

    //init viewmodelvirtualmachine


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmViewModel.virtualMachineList.value?.get(0)?.name

        binding.loginButton.setOnClickListener {


        }
    }


}
