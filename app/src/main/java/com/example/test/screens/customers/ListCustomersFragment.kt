package com.example.test.screens.customers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.test.R
import com.example.test.databinding.ListCustomersFragmentBinding

/**
 * List customers fragment
 *
 * @constructor Create empty List customers fragment
 */
class ListCustomersFragment : Fragment() {

    private val viewModel: ListCustomersViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, ListCustomersViewModelFactory(activity.application)).get(
            ListCustomersViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: ListCustomersFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.list_customers_fragment, container, false)

        binding.listCustomersViewModel = viewModel
        binding.lifecycleOwner = this
        Log.d("onclick", "customerClickListener executed")
        val adapter = ListCustomersAdapter(CustomerListener { customerId ->
            findNavController().navigate(
                (
                        ListCustomersFragmentDirections.actionListCustomerFragmentToCustomerFragment(
                            customerId
                        )
                        )
            )
            Toast.makeText(context, "$customerId", Toast.LENGTH_SHORT).show()
            Log.d("onclick", "customerClickListener executed")
        })
        val recyclerView = binding.customerList
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )

        viewModel.customers.observe(viewLifecycleOwner) { adapter.submitList(it) }

        return binding.root
    }
}
