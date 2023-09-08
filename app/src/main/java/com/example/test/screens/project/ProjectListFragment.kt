package com.example.test.screens.project

import android.os.Bundle
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
import com.example.test.R
import com.example.test.databinding.ProjectFragmentItemListBinding

/**
 * Project list fragment
 *
 * @constructor Create empty Project list fragment
 */
class ProjectListFragment : Fragment() {

    companion object {
        fun newInstance() = ProjectListFragment()
    }

    private lateinit var binding: ProjectFragmentItemListBinding
    private lateinit var adapter: ProjectListAdapter
    private val viewModel: ProjectListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, ProjectListViewModelFactory(activity.application)).get(
            ProjectListViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.project_fragment_item_list, container, false)
        binding.listProjectViewModel = viewModel
        binding.lifecycleOwner = this

        adapter = ProjectListAdapter(ProjectListener {

            findNavController().navigate(
                ProjectListFragmentDirections.actionProjectListFragmentToVirtualMachineListFragment()
            )

        })
        val recyclerView = binding.projectNames
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL)
        )

        viewModel.projects.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })


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


    private fun filterItems(query: String?) {
        val filteredList = viewModel.projects.value?.filter { item ->
            item.name.contains(query ?: "", ignoreCase = true) // Adjust filtering logic as needed
        }

        adapter.submitList(filteredList)
    }


}