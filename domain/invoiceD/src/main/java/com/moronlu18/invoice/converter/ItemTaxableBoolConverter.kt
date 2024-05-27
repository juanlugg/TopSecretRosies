package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class ItemTaxableBoolConverter {

    @TypeConverter
    fun fromBool(value: Boolean): String {
        return if (value) "true" else "false"
    }

    @TypeConverter
    fun toBool(value: String): Boolean {
        return value == "true"
    }
}