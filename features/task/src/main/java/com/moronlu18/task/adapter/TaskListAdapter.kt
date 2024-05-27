package com.moronlu18.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.taskFragment.R
import com.moronlu18.taskFragment.databinding.RowTaskListBinding


class TaskListAdapter(
    private val onClick: (task: Task, nav: Int) -> Unit,
    private val onDelete: (task: Task) -> Unit,
) : ListAdapter<Task, TaskListAdapter.ViewHolder>(TASK_COMPARATOR) {

    inner class ViewHolder(var binding: RowTaskListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                tvTitleTaskList.text = task.title
                tvCustomerTaskList.text = task.customer.getFullName()
                tvDCreateTaskList.text = task.createdDate
                tvDEndTaskList.text = task.endDate
                ivTaskList.setImageResource( when(task.status){
                    TaskStatus.overdue -> R.drawable.icon_taskoverdue
                    TaskStatus.modified -> R.drawable.icon_taskmodified
                    TaskStatus.pending -> R.drawable.icon_taskpending
                })
                cvTaskAdapter.setOnClickListener {
                    onClick(task, 0)
                }
                ivEdit.setOnClickListener {
                    onClick(task, 1)
                }
                ivDelete.setOnClickListener {
                    onDelete(task)
                    //tasks.removeAt(position)
                    //notifyDataSetChanged()
                    //notifyItemRemoved(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(RowTaskListBinding.inflate(layoutInflater, parent, false))
    }
    fun sortId() {
        val sortedTaskList = currentList.sortedBy { it.idTask.value }
        submitList(sortedTaskList)
    }
    fun sortCustomer() {
        val sortedTaskList = currentList.sortedBy { it.customer.nombre }
        submitList(sortedTaskList)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)

        holder.bind(task)
    }

    companion object {
        private val TASK_COMPARATOR = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.idTask == newItem.idTask
            }

        }
    }
}



