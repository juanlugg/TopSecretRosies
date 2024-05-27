package com.moronlu18.task.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moronlu18.customer.entity.Customer
import com.moronlu18.invoice.converter.TaskCustomerIdConverter
import com.moronlu18.invoice.converter.TaskIdConverter
import com.moronlu18.invoice.converter.TaskStatusConverter
import com.moronlu18.invoice.entity.UniqueId
import java.io.Serializable

@Entity(
    tableName = "task",foreignKeys =
    [ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("customer"),
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey
    @TypeConverters(TaskIdConverter::class)
    val idTask: TaskId,
    @TypeConverters(TaskCustomerIdConverter::class)
    var customer: Customer,
    var title: String,
    var description: String,
    @TypeConverters(TaskStatusConverter::class)
    var type: TaskType,
    @TypeConverters(TaskStatusConverter::class)
    var status: TaskStatus,
    var createdDate: String,
    var endDate: String
) : Serializable

data class TaskId(override val value: Int) : UniqueId(value)