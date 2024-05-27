package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus

@ProvidedTypeConverter
class InvoiceStatusConverter {
    @TypeConverter
    fun toStatus(value: String): InvoiceStatus {
        return InvoiceStatus.valueOf(value)
    }
    @TypeConverter
    fun fromLong(status : InvoiceStatus):String{
        return status.toString()
    }
}