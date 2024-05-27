package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.item.entity.itemType
@ProvidedTypeConverter
class ItemTypeConverter {

    @TypeConverter
    fun fromItemType(value: itemType): String {
        return value.name
    }

    @TypeConverter
    fun toItemType(value: String): itemType {
        return enumValueOf(value)
    }
}
