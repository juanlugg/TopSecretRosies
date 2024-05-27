package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.InvoiceDavid.entity.InvoiceId

@ProvidedTypeConverter
class InvoiceIdTypeConverter {
    @TypeConverter
    fun toInvoiceId(value: Int): InvoiceId{
        return InvoiceId(value)
    }
    @TypeConverter
    fun fromInvoiceId(invoiceId : InvoiceId):Int{
        return invoiceId.value
    }
}