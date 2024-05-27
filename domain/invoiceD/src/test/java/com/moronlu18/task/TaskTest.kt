package com.moronlu18.task

import com.google.common.truth.Truth
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.ProviderCustomer
import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskId
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import org.junit.Test

class TaskTest {
    private val task : Task = Task(TaskId(1),ProviderCustomer.GetCliente(1)!!, "Crear Test", "Crear una prueba", TaskType.private, TaskStatus.overdue, "29/02/2024", "29/02/2024")
    @Test
    fun `task_TaskId is Instance of TaskId`(){
        Truth.assertThat(task.idTask).isInstanceOf(TaskId::class.java)
    }
    @Test
    fun `task_TaskId is equals B`(){
        val b = TaskId(1)
        Truth.assertThat(task.idTask).isEqualTo(b)
    }
    @Test
    fun `TaskType is correct`(){
        Truth.assertThat(task.type).isEqualTo(TaskType.private)
    }
    @Test
    fun `TaskType is incorrect`(){
        Truth.assertThat(task.type).isNotEqualTo(TaskType.call)
    }
    @Test
    fun `TaskStatus is correct`(){
        Truth.assertThat(task.status).isEqualTo(TaskStatus.overdue)
    }
    @Test
    fun `TaskStatus is incorrect`(){
        Truth.assertThat(task.status).isNotEqualTo(TaskStatus.modified)
    }

    @Test
    fun `Create a Task`(){
        val idTask = TaskId(1)
        val customer: Customer = ProviderCustomer.GetCliente(1)!!
        val title = "Crear Test"
        val description = "Crear una prueba"
        val type: TaskType = TaskType.private
        val status: TaskStatus = TaskStatus.overdue
        val createdDate = "29/02/2024"
        val endDate = "29/02/2024"
        Truth.assertThat(task).isEqualTo(Task(idTask,customer,title,description,type,status,createdDate,endDate))
    }
    @Test
    fun `task is Instance Of Task`(){
        Truth.assertThat(task).isInstanceOf(Task::class.java)
    }
    @Test
    fun `customer is correct`(){
        Truth.assertThat(task.customer).isEqualTo(ProviderCustomer.GetCliente(1))
    }
}