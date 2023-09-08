package com.example.test.screens.virtual_machines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.VmItemBinding
import com.example.test.domain.VirtualMachine

/**
 * Virtual machines adapter
 *
 * @property clickListener
 * @constructor Create empty Virtual machines adapter
 */
class VirtualMachinesAdapter(val clickListener: VirtualMachineListener) :
    ListAdapter<VirtualMachine, VirtualMachineViewHolder>(VirtualMachineListDiffCallback()) {

    override fun onBindViewHolder(holder: VirtualMachineViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VirtualMachineViewHolder {
        return VirtualMachineViewHolder.from(parent)
    }

}

/**
 * Virtual machine view holder
 *
 * @property binding
 * @constructor Create empty Virtual machine view holder
 */
class VirtualMachineViewHolder(val binding: VmItemBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Bind
     *
     * @param clickListener
     * @param item
     */
    fun bind(clickListener: VirtualMachineListener, item: VirtualMachine) {


        binding.vmActiveTextview.text = item.active


        binding.vmNameTextview.text = item.name

        binding.vmVcpuTextview.text = item.vcpUAmount.toString()
        binding.vmStorageTextview.text = item.storageAmount.toString() + "GB"
        binding.vmMemoryTextview.text = item.memoryAmount.toString() + "GB"

        binding.vmItem = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): VirtualMachineViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = VmItemBinding.inflate(layoutInflater, parent, false)
            return VirtualMachineViewHolder(binding)
        }
    }
}

/**
 * Virtual machine list diff callback
 *
 * @constructor Create empty Virtual machine list diff callback
 */
class VirtualMachineListDiffCallback : DiffUtil.ItemCallback<VirtualMachine>() {
    override fun areItemsTheSame(oldItem: VirtualMachine, newItem: VirtualMachine): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VirtualMachine, newItem: VirtualMachine): Boolean {
        // Works perfectly because it's a dataclass
        return oldItem == newItem
    }
}

/**
 * Virtual machine listener
 *
 * @property clickListener
 * @constructor Create empty Virtual machine listener
 */
class VirtualMachineListener(val clickListener: (vmId: Int) -> Unit) {
    /**
     * On click
     *
     * @param virtualMachine
     */
    fun onClick(virtualMachine: VirtualMachine) = clickListener(virtualMachine.id)
}