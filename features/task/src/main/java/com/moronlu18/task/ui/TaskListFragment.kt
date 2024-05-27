package com.moronlu18.task.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.invoice.MainActivity
import com.moronlu18.invoice.ui.utils.Notification
import com.moronlu18.task.adapter.TaskListAdapter
import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.usecase.TaskViewModel
import com.moronlu18.taskFragment.R
import com.moronlu18.taskFragment.databinding.FragmentTaskListBinding

@RequiresApi(Build.VERSION_CODES.O)
class TaskListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var taskListAdapter : TaskListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        channel = NotificationChannel(
            CHANNEL_ID,
            "Channel Invoice",
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = "Invoice Borrado"
        }

        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        setUpToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskListAdapter = TaskListAdapter ({ task : Task, nav:Int ->
            var bundle = Bundle()
            bundle.putSerializable("task",task)
            parentFragmentManager.setFragmentResult("key",bundle)
            if(nav == 0){
                findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment)
            } else if(nav == 1){
                findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)
            }
        },{ task : Task ->
            showDeleteConfirmationDialog(task)
        })
        binding.rvTaskList.adapter = taskListAdapter
        binding.rvTaskList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allTasks.observe(viewLifecycleOwner){tasks ->
            if(tasks.isEmpty()){
                binding.avNoData.visibility = View.VISIBLE
                binding.rvTaskList.visibility = View.GONE
            }else{
                binding.avNoData.visibility = View.GONE
                binding.rvTaskList.visibility = View.VISIBLE
                taskListAdapter.submitList(tasks)
                val count = tasks.count { task -> task.status in setOf(TaskStatus.pending, TaskStatus.modified)} // Considero el estado modified como no completado tambien
                Notification.showNotification(requireContext(),"Tareas pendientes","Tienes $count tareas sin completar",
                    channel, CHANNEL_ID, NOTIFICATION_ID)
            }
        }

        binding.fabTaskList.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.refreshTask -> {
                taskListAdapter.sortId()
                binding.rvTaskList.adapter?.notifyDataSetChanged()
                true
            }
            R.id.sortTask -> {
                taskListAdapter.sortCustomer()
                binding.rvTaskList.adapter?.notifyDataSetChanged()
                true
            }else -> false
        }
    }

    private fun setUpToolbar() {
        //Modismo aplly de kotlin
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }
        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun showDeleteConfirmationDialog(task: Task) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("¿Deseas eliminar esta tarea?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            viewModel.deleteTask(task)
            if (taskListAdapter.currentList.size < 1) {
                binding.rvTaskList.visibility = View.GONE
                binding.avNoData.visibility = View.VISIBLE
            } else {
                binding.rvTaskList.visibility = View.VISIBLE
                binding.avNoData.visibility = View.GONE
            }
            binding.rvTaskList.adapter?.notifyDataSetChanged()
        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    companion object{
        lateinit var channel: NotificationChannel
        private val NOTIFICATION_ID = 800
        private val CHANNEL_ID = "delete_chanel"
    }
}

