package com.example.test.screens.customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.test.R
import com.example.test.databinding.CustomerDetailFragmentBinding
import com.example.test.screens.virtual_machines.VirtualMachineListViewModel
import com.example.test.screens.virtual_machines.VirtualMachineListViewModelFactory
import com.example.test.screens.virtual_machines.VirtualMachineListener
import com.example.test.screens.virtual_machines.VirtualMachinesAdapter

/**
 * Customer fragment
 *
 * @constructor Create empty Customer fragment
 */
class CustomerFragment : Fragment() {

    //binding
    private lateinit var binding: CustomerDetailFragmentBinding
    private lateinit var adapter: VirtualMachinesAdapter


    private val vmViewModel: VirtualMachineListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, VirtualMachineListViewModelFactory(activity.application)).get(
            VirtualMachineListViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = CustomerFragmentArgs.fromBundle(requireArguments())


        val viewModel: CustomerViewModel by lazy {
            val activity = requireNotNull(this.activity)
            ViewModelProvider(
                this,
                CustomerViewModelFactory(activity.application, args.customerId)
            ).get(
                CustomerViewModel::class.java
            )
        }
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.customer_detail_fragment,
            container,
            false
        )


        //viewModel
//        val activity = requireNotNull(this.activity)
//
//        val viewModelFactory = CustomerViewModelFactory(activity.application, args.customerId)
//        val viewModel: CustomerViewModel by viewModels{viewModelFactory}

        // Set the viewmodel for databinding - this allows the bound layout access to all of the
        // data in the VieWModel
        binding.customerViewModel = viewModel

//        binding.textviewName.text = viewModel.backupCustomer.value?.name
//        binding.textviewEmail.text = viewModel.backupCustomer.value?.email
//        binding.textviewName.text = viewModel.backupCustomer.value?.name
//        binding.textviewPhonenr.text = viewModel.backupCustomer.value?.phoneNr

        //Add items to the ListView and make it clickable


        binding.virtualMachineListViewModel =
            vmViewModel        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.lifecycleOwner = this

        adapter = VirtualMachinesAdapter(VirtualMachineListener { virtualMachineID ->
            findNavController().navigate(
                CustomerFragmentDirections.actionCustomerFragmentToVirtualMachineDetailFragment(

                    virtualMachineID
                )
            )
        })

        val recyclerView = binding.virtualMachineList
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL)
        )

        vmViewModel.virtualMachineList.observe(viewLifecycleOwner, Observer {
            println("List got ${it.size}")
            adapter.submitList(it)
        })


        return binding.root
    }


}
