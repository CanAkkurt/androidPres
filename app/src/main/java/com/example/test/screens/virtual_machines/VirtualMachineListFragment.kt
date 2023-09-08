package com.example.test.screens.virtual_machines

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
import com.example.test.databinding.FragmentVirtualMachineListBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * Virtual machine list fragment
 *
 * @constructor Create empty Virtual machine list fragment
 */
class VirtualMachineListFragment : Fragment() {
    companion object {
        fun newInstance() = VirtualMachineListFragment()
    }

    private lateinit var binding: FragmentVirtualMachineListBinding
    private lateinit var adapter: VirtualMachinesAdapter
    private val viewModel: VirtualMachineListViewModel by lazy {
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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_virtual_machine_list,
            container,
            false
        )
        binding.virtualMachineListViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = VirtualMachinesAdapter(VirtualMachineListener { virtualMachineID ->
            findNavController().navigate(
                VirtualMachineListFragmentDirections.actionVirtualMachineListFragmentToVirtualMachineDetailFragment(
                    virtualMachineID
                )
            )
        })

        val recyclerView = binding.virtualMachineList
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )

        viewModel.virtualMachineList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        //
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
            adapter.submitList(viewModel.virtualMachineList.value) // Replace 'yourFullList' with your complete data source
        } else {

            val filteredData = viewModel.virtualMachineList.value?.filter { vm ->
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
        val filteredList = viewModel.virtualMachineList.value?.filter { item ->
            item.name.contains(query ?: "", ignoreCase = true) // Adjust filtering logic as needed
        }

        adapter.submitList(filteredList)
    }


}



