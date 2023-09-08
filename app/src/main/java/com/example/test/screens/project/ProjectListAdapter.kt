package com.example.test.screens.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ProjectItemBinding
import com.example.test.domain.Project

/**
 * Project list adapter
 *
 * @property clickListener
 * @constructor Create empty Project list adapter
 */
class ProjectListAdapter(val clickListener: ProjectListener) :
    ListAdapter<Project, ProjectViewHolder>(ProjectDiffCallback()) {

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder.from(parent)
    }

}

/**
 * Project view holder
 *
 * @property binding
 * @constructor Create empty Project view holder
 */
class ProjectViewHolder(val binding: ProjectItemBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Bind
     *
     * @param clickListener
     * @param item
     */
    fun bind(clickListener: ProjectListener, item: Project) {

        binding.textviewCustomerLabel.text = item.customerName
        binding.vmNameTextview.text = item.name
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ProjectViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProjectItemBinding.inflate(layoutInflater, parent, false)
            return ProjectViewHolder(binding)
        }
    }
}

/**
 * Project diff callback
 *
 * @constructor Create empty Project diff callback
 */
class ProjectDiffCallback : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        // Works perfectly because it's a dataclass
        return oldItem == newItem
    }
}

/**
 * Project listener
 *
 * @property clickListener
 * @constructor Create empty Project listener
 */
class ProjectListener(val clickListener: (projectName: String) -> Unit) {
    /**
     * On click
     *
     * @param project
     */
    fun onClick(project: Project) = clickListener(project.name)
}