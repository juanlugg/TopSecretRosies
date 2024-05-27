package com.moronlu18.invoice.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.CustomerRepository

@ProvidedTypeConverter
class TaskCustomerIdConverter {
    @TypeConverter
    fun toCustomer(id: Int): Customer?{
        return CustomerRepository.getCustomerListRAW().find { it.id.value == id }
    }
    @TypeConverter
    fun fromCustomer(value: Customer): Int {
        return value.id.value
    }
}