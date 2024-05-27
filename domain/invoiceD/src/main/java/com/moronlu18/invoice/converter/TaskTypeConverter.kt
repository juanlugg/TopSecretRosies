package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.task.entity.TaskType

@ProvidedTypeConverter

class TaskTypeConverter {
    @TypeConverter
    fun toType(value: String): TaskType {
        return TaskType.valueOf(value)
    }
    @TypeConverter
    fun fromType(type: TaskType):String{
        return type.toString()
    }
}