package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import java.time.Instant

@ProvidedTypeConverter
class InvoiceInstantLongConverter {
    @TypeConverter
    fun toLong(value: Instant): Long {
        return value.toEpochMilli()
    }
    @TypeConverter
    fun fromLong(long : Long):Instant{
        return Instant.ofEpochMilli(long)
    }
}