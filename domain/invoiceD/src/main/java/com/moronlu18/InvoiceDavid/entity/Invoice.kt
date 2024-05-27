package com.moronlu18.invoice.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.invoice.converter.CustomerIDTypeConverter
import com.moronlu18.invoice.converter.InvoiceIdTypeConverter
import com.moronlu18.invoice.converter.InvoiceInstantLongConverter
import com.moronlu18.invoice.converter.InvoiceStatusConverter
import java.io.Serializable
import java.time.Instant

@Entity(
    tableName = "invoice", foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("id"), childColumns = arrayOf("idCliente"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
    )]
)
data class Invoice(
    @PrimaryKey
    @TypeConverters(InvoiceIdTypeConverter::class)
    val id: InvoiceId,
    @TypeConverters(CustomerIDTypeConverter::class)
    val idCliente: CustomerId,
    @TypeConverters(InvoiceInstantLongConverter::class)
    val FeEmision: Instant,
    @TypeConverters(InvoiceInstantLongConverter::class)
    val FeVencimiento: Instant,
    @TypeConverters(InvoiceStatusConverter::class)
    val Estado: InvoiceStatus,
    val name: String
) : Serializable {


}

