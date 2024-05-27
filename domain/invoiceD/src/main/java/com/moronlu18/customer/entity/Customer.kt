package com.moronlu18.customer.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moronlu18.invoice.converter.CustomerEmailTypeConverter
import com.moronlu18.invoice.converter.CustomerIDTypeConverter
import com.moronlu18.invoice.ui.firebase.Email
import java.io.Serializable

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey
    @TypeConverters(CustomerIDTypeConverter::class)
    val id: CustomerId,
    @NonNull
    val nombre: String,
    val apellidos: String?,
    @NonNull
    @TypeConverters(CustomerEmailTypeConverter::class)
    val email: Email,
    val telefono: String?,
    val city: String?,
    val direction: String?
):Serializable {
    public fun getFullName(): String {
        return this.nombre + " " + this.apellidos
    }
}
