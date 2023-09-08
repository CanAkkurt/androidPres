package com.example.test.screens.members

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.ListMembersFragmentBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MemberListFragment() : Fragment() {


    private lateinit var binding: ListMembersFragmentBinding
    private lateinit var adapter: MemberListAdapter

    private val viewModel: MemberListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MemberListViewModelFactory(activity.application)).get(
            MemberListViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.list_members_fragment, container, false)

        binding.listMembersViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = MemberListAdapter(MemberListener { memberID ->
            findNavController().navigate(
                MemberListFragmentDirections.actionListMembersFragmentToMemberFragment(
                    memberID
                )
            )
        })
        val recyclerView = binding.memberList
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )

        viewModel.listMembers.observe(viewLifecycleOwner) { adapter.submitList(it) }

        val chipGroup: ChipGroup = binding.chipGroup
// Sample filter options
        val filterOptions = listOf("Admin", "Customer", "Manager", "User")

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
        adapter: MemberListAdapter
    ) {
        // Implement your filtering logic here for members
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
            adapter.submitList(viewModel.listMembers.value) // Replace 'yourFullList' with your complete data source
        } else {

            val filteredData = viewModel.listMembers.value?.filter { member ->
                // Apply filtering logic here
                selectedFilters.all { filter ->
                    filter.equals(member.role.name, ignoreCase = true)

                }

            }
            adapter.submitList(filteredData)
        }

    }

    private fun filterItems(query: String?) {
        val filteredList = viewModel.listMembers.value?.filter { item ->
            item.name.contains(query ?: "", ignoreCase = true) // Adjust filtering logic as needed
        }

        adapter.submitList(filteredList)
    }
}