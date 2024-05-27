package com.moronlu18.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import com.moronlu18.task.entity.Task
import com.moronlu18.task.usecase.TaskViewModel
import com.moronlu18.taskFragment.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key", this,
            FragmentResultListener { _, result ->
                val task: Task = result.getSerializable("task") as Task
                //val task = TaskRepository.selectAllTaskListRAW()[pos]
                binding.tvTaskDetailTitle.text = task.title
                binding.tvTaskDetailClienteCont.text = task.customer.getFullName()
                binding.tvTaskDetailDescCont.text = task.description
                binding.tvTaskDetailDateStartCont.text = task.createdDate
                binding.tvTaskDetailDateEndCont.text = task.endDate
                binding.tvTaskDetailTypeCont.text = task.type.toString()
                binding.tvTaskDetailStateCont.text = task.status.toString()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}