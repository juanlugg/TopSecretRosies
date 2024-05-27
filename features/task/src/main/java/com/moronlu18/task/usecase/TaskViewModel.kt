package com.moronlu18.task.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.signup.utils.Locator
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.invoice.ui.utils.calendar.CalendarInvoice
import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskId
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import com.moronlu18.task.repository.TaskRepository
import com.moronlu18.task.ui.TaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class TaskViewModel : ViewModel() {
    var allTasks = orderList()
    var idTask = MutableLiveData<Int>()
    val title = MutableLiveData<String>()
    val idCustomer = MutableLiveData<Int>() //Recibe la posicion del spinner de customer
    val description = MutableLiveData<String>()
    private val type = MutableLiveData<TaskType>()
    private val status = MutableLiveData<TaskStatus>()
    val createdDate = MutableLiveData(CalendarInvoice.getCurrentDate())
    val endDate = MutableLiveData<String>()
    val typeSelected = MutableLiveData<Int>()
    val statusSelected = MutableLiveData<Int>()
    var isEdit: Boolean = false


    private val state = MutableLiveData<TaskState>()
    fun validate() {
        val isNotCustomerSelected =
            idCustomer.value == 0 //Si no se ha seleccionado ninguno, devolverá true
        when {
            TextUtils.isEmpty(title.value) || title.value?.isBlank()!! -> state.value =
                TaskState.TitleIsMandatoryError

            isNotCustomerSelected -> state.value = TaskState.CustomerUnspecifiedError
            incorrectDateRange(createdDate.value, endDate.value) -> state.value =
                TaskState.IncorrectDateRangeError

            else -> {
                state.value = TaskState.Success
            }
        }
    }

    fun orderList(): LiveData<List<Task>> {
        return when (Locator.invoicePreferencesRepository.getTaskOrder()) {
            "Id" -> TaskRepository.selectAllTaskList()
            "Customer" -> TaskRepository.selectAllTaskListOrderByCustomer()
            else -> {
                TaskRepository.selectAllTaskList()
            }
        }.asLiveData()
    }

    fun refreshList(){
       allTasks = orderList()
    }

    /**
     * Función que crea o edita una tarea
     */
    fun makeTask() {
        val customerId = idCustomer.value!!
        val title = this.title.value!!
        //val nameCustomer = ProviderCustomer.datasetCustomer.find { it.id.value == customerId }?.getFullName()!!
        val desc = description.value ?: "" //Puede no tener descripión
        val type = getType()
        val status = getStatus()
        val createdDate = this.createdDate.value!!
        val endDate = this.endDate.value ?: "" //Puede tener fecha fin vacio
        if (idTask.value == null)
            idTask.value =
                TaskRepository.selectAllTaskListRAW().lastOrNull()?.idTask?.value?.plus(1)
                    ?: 1 //si no esta vacio devuelve el ultimo id + 1, si esta vacio devuelve 1
        val task = Task(TaskId(idTask.value!!), getCustomerList().find { it.id.value == customerId }!!, title, desc, type, status, createdDate, endDate)
        viewModelScope.launch(Dispatchers.IO) {
            when (isEdit) {
                true -> TaskRepository.updateTask(task)
                false -> TaskRepository.insertTask(task)
            }
        }
    }

    fun deleteTask(task: Task) {
        TaskRepository.deleteTask(task)
    }

    fun getTaskList(): List<Task> {
        return TaskRepository.selectAllTaskListRAW()
    }

    fun getCustomerList(): List<Customer> {
        return CustomerRepository.getCustomerListRAW()
    }

    /**
     * Función que devuelve si es incorrecto el rango de fechas (createdDate antes que endDate)
     */
    private fun incorrectDateRange(created: String?, end: String?): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        if (end.isNullOrBlank()) {
            return false //Si no hay fecha fin, el rango no es incorrecto
        }
        val createdDate = dateFormat.parse(created!!) //Siempre tendrá un valor por defecto
        val endDate = dateFormat.parse(end)
        return createdDate!!.after(endDate)
    }

    /**
     * Función que devuelve el tipo de tarea según la posición del spinner
     */
    private fun getType(): TaskType {
        when (typeSelected.value) {
            //0 -> type.value = TaskType.private
            1 -> type.value = TaskType.call
            2 -> type.value = TaskType.visitor
            else -> type.value = TaskType.private
        }
        return type.value!!
    }

    /**
     * Función que devuelve el estado de tarea según la posición del spinner
     */
    private fun getStatus(): TaskStatus {
        when (statusSelected.value) {
            //0 -> status.value = TaskStatus.pending
            1 -> status.value = TaskStatus.modified
            2 -> status.value = TaskStatus.overdue
            else -> status.value = TaskStatus.pending
        }
        return status.value!!
    }

    /**
     * Función que devuelve el estado de la creación de la tarea
     */
    fun getState(): LiveData<TaskState> {
        return state;
    }
}