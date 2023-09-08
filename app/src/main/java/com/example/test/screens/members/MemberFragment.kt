package com.example.test.screens.members


import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.MemberDetailFragmentBinding
import com.example.test.screens.virtual_machines.VirtualMachineListViewModel
import com.example.test.screens.virtual_machines.VirtualMachineListViewModelFactory
import com.example.test.screens.virtual_machines.VirtualMachineListener
import com.example.test.screens.virtual_machines.VirtualMachinesAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


/**
 * Member fragment
 *
 * @constructor Create empty Member fragment
 */
class MemberFragment : Fragment() {
    //binding
    private lateinit var binding: MemberDetailFragmentBinding
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
        val args = MemberFragmentArgs.fromBundle(requireArguments())

        val viewModel: MemberViewModel by lazy {
            val activity = requireNotNull(this.activity)
            ViewModelProvider(
                this,
                MemberViewModelFactory(activity.application, args.memberId)
            ).get(
                MemberViewModel::class.java
            )
        }



        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.member_detail_fragment,
            container,
            false
        )


        binding.membersViewModel = viewModel
        // set virtualmachinelist data in lifecycyle


//        val vmViewModelFactory = VirtualMachineListViewModelFactory(activity.application);
//        val vmViewModel = ViewModelProvider(this, vmViewModelFactory).get(VirtualMachineListViewModel::class.java)
        binding.virtualMachineListViewModel = vmViewModel

        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.lifecycleOwner = this
        //action_memberFragment_to_virtualMachineDetailFragment
        adapter = VirtualMachinesAdapter(VirtualMachineListener { virtualMachineID ->
            findNavController().navigate(
                MemberFragmentDirections.actionMemberFragmentToVirtualMachineDetailFragment
                    (
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

        //filtering
        val chipGroup: ChipGroup = binding.chipGroup
// Sample filter options
        val filterOptions = listOf("Active", "Not Active", "Requested", "Processing", "Denied")

        for (option in filterOptions) {

            val chip =
                Chip(ContextThemeWrapper(chipGroup.context, R.style.Theme_MaterialComponents_Light))
            chip.text = option
            chip.isCheckable = true

            // Set a click listener to handle chip selection
            chip.setOnCheckedChangeListener { _, _ ->
                // Call a method to apply filters to your RecyclerView
                applyFilters(chipGroup, recyclerView, adapter)
            }

            chipGroup.addView(chip)
        }
        // End of sample filter options
        // searchbar
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText)
                return true
            }
        })



        return binding.root
    }


    private fun applyFilters(
        chipGroup: ChipGroup,
        RecyclerView: RecyclerView,
        adapter: VirtualMachinesAdapter
    ) {
        // Implement your filtering logic here for virtualmachinelist
        // You can call adapter.submitList() here with the filtered list
        val selectedFilters = mutableListOf<String>()

        // Iterate through selected chips in the ChipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedFilters.add(chip.text.toString())
            }
        }

        // Filter the data based on the selected chips in virtualmachinelist
        if (selectedFilters.isEmpty()) {
            // If no chips are selected, show the full list
            adapter.submitList(vmViewModel.virtualMachineList.value) // Replace 'yourFullList' with your complete data source
        } else {

            val filteredData = vmViewModel.virtualMachineList.value?.filter { vm ->
                // Apply filtering logic here
                selectedFilters.all { filter ->
                    filter.equals(vm.active, ignoreCase = true) ||
                            filter.equals(vm.state.name, ignoreCase = true)

                }

            }
            adapter.submitList(filteredData)
        }

    }


    private fun filterItems(query: String?) {
        val filteredList = vmViewModel.virtualMachineList.value?.filter { item ->
            item.name.contains(query ?: "", ignoreCase = true) // Adjust filtering logic as needed
        }

        adapter.submitList(filteredList)
    }


}