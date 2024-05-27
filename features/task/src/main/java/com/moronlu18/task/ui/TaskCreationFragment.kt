package com.moronlu18.task.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.moronlu18.invoice.ui.utils.Utils
import com.moronlu18.invoice.ui.utils.calendar.CalendarInvoice
import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import com.moronlu18.task.usecase.TaskViewModel
import com.moronlu18.taskFragment.databinding.FragmentTaskCreationBinding


class TaskCreationFragment : Fragment() {
    private val c = CalendarInvoice()

    private var _binding: FragmentTaskCreationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()


    inner class TextWatcherTask(private var til: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            til.isErrorEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeWidgetsValues()
        parentFragmentManager.setFragmentResultListener("key", this,
            FragmentResultListener { _, result ->
                val task: Task = result.getSerializable("task") as Task
                //val task = viewModel.getTask(pos)
                viewModel.idTask.value = task.idTask.value
                binding.tieTaskCreationTitle.setText(task.title)
                binding.spTaskCreationCustomer.setSelection(task.customer.id.value)
                binding.tieTaskCreationDesc.setText(task.description)
                binding.tieTaskCreationDateStart.setText(task.createdDate)
                binding.tieTaskCreationDateEnd.setText(task.endDate)
                binding.spTaskCreationType.setSelection(task.type.ordinal)
                binding.spTaskCreationStatus.setSelection(task.status.ordinal)
                viewModel.isEdit = true
            })


        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                TaskState.TitleIsMandatoryError -> {
                    binding.tilTaskCreationTitle.error = "Titulo obligatorio"
                }

                TaskState.CustomerUnspecifiedError -> {
                    Utils.showToast(requireContext(), "Selecciona un cliente")
                }

                TaskState.IncorrectDateRangeError -> {
                    binding.tilTaskCreationDateEnd.error =
                        "La fecha fin no puede ser menor que la fecha inicio"
                }

                TaskState.Success -> {
                    viewModel.makeTask()
                    if (viewModel.isEdit)
                        Utils.showToast(requireContext(), "Tarea editada")
                    else
                        Utils.showToast(requireContext(), "La tarea ha sido creada")

                    findNavController().popBackStack()
                }
            }
        }

        binding.btnTaskCreationAdd.setOnClickListener {
            viewModel.validate()
        }

        binding.tieTaskCreationTitle.addTextChangedListener {
            TextWatcherTask(binding.tilTaskCreationTitle)
        }
    }

    /**
     * Inicializa los spinners y los tie date que hay en taskcreation
     */
    @SuppressLint("SetTextI18n")
    private fun initializeWidgetsValues() {
        //PopUp de Calendario
        binding.tieTaskCreationDateStart.setOnClickListener {
            c.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieTaskCreationDateStart.setText("$day/${month + 1}/$year")
            }
        }
        binding.tieTaskCreationDateEnd.setOnClickListener {
            c.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieTaskCreationDateEnd.setText("$day/${month + 1}/$year")
            }
        }

        val names: MutableList<String> = mutableListOf()
        //Añade los clientes al spinner y si no hay no puedes crear una tarea
        //val customerList = CustomerRepository.getCustomerListRAW()
        if (viewModel.getCustomerList().isEmpty()) {
            names.add("<No Existen Clientes>")
            binding.btnTaskCreationAdd.isEnabled = false
        } else {
            names.add("--Selecciona un cliente--")
            for (customer in viewModel.getCustomerList()) {
                names.add("${customer.id.value}.-" + customer.getFullName())
            }
        }
        binding.spTaskCreationCustomer.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, names)

        binding.spTaskCreationType.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                TaskType.values()
            )

        binding.spTaskCreationStatus.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                TaskStatus.values()
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}