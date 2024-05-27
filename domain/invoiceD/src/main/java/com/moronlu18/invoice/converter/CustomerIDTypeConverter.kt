package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.customer.entity.CustomerId

@ProvidedTypeConverter
class CustomerIDTypeConverter {
    @TypeConverter
    fun toInvoiceId(value: Int): CustomerId {
        return CustomerId(value)
    }
    @TypeConverter
    fun fromInvoiceId(customerId: CustomerId):Int{
        return customerId.value
    }
}