package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.task.entity.TaskStatus

@ProvidedTypeConverter
class TaskStatusConverter {
    @TypeConverter
    fun toStatus(value: String): TaskStatus {
        return TaskStatus.valueOf(value)
    }
    @TypeConverter
    fun fromStatus(status : TaskStatus):String{
        return status.toString()
    }
}