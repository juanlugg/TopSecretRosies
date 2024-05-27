package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.task.entity.TaskId

@ProvidedTypeConverter
class TaskIdConverter {
    @TypeConverter
    fun toTaskId(value: Int): TaskId {
        return TaskId(value)
    }
    @TypeConverter
    fun fromTaskId(taskId: TaskId):Int{
        return taskId.value
    }
}