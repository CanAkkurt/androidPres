package com.example.test.screens.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.CustomerListItemBinding
import com.example.test.domain.Customer

/**
 * List customers adapter
 *
 * @property clickListener
 * @constructor Create empty List customers adapter
 */
class ListCustomersAdapter(val clickListener: CustomerListener) :
    ListAdapter<Customer, ViewHolder>(CustomerDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}

/**
 * View holder
 *
 * @property binding
 * @constructor Create empty View holder
 */
class ViewHolder(val binding: CustomerListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Bind
     *
     * @param clickListener
     * @param item
     */
    fun bind(clickListener: CustomerListener, item: Customer) {
        binding.textviewName.text = item.name
//        binding.textviewDepartment.text = item.department.toString()
        binding.customer = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CustomerListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

/**
 * Customer diff callback
 *
 * @constructor Create empty Customer diff callback
 */
class CustomerDiffCallback : DiffUtil.ItemCallback<Customer>() {
    override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        // Works perfectly because it's a dataclass
        return oldItem == newItem
    }
}

/**
 * Customer listener
 *
 * @property clickListener
 * @constructor Create empty Customer listener
 */
class CustomerListener(val clickListener: (customerId: Int) -> Unit) {
    /**
     * On click
     *
     * @param customer
     */
    fun onClick(customer: Customer) = clickListener(customer.id)
}