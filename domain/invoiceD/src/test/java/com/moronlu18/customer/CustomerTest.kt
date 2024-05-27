package com.moronlu18.customer

import com.google.common.truth.Truth
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.invoice.ui.firebase.Email
import org.junit.Test

class CustomerTest {
    private val customer: Customer = Customer(
        CustomerId(1),
        "Alex",
        "Carnero",
        Email("carnetaadspjf@gmail.com"),
        "682027895",
        "Málaga",
        "Calle Leonora n46"
    )
    @Test
    fun`Customer Creation`(){
        val idCustomer=CustomerId(1)
        val nombre= "Alex"
        val apellidos = "Carnero"
        val email =Email("carnetaadspjf@gmail.com")
        val telefono = "682027895"
        val ciudad ="Málaga"
        val direccion = "Calle Leonora n46"
        val cliente =Customer(idCustomer,nombre,apellidos,email,telefono,ciudad,direccion)
        Truth.assertThat(customer.id).isEqualTo(cliente.id)
        Truth.assertThat(customer.nombre).isEqualTo(cliente.nombre)
        Truth.assertThat(customer.apellidos).isEqualTo(cliente.apellidos)
        Truth.assertThat(customer.email.value).isEqualTo(cliente.email.value)
        Truth.assertThat(customer.telefono).isEqualTo(cliente.telefono)
        Truth.assertThat(customer.city).isEqualTo(cliente.city)
        Truth.assertThat(customer.direction).isEqualTo(cliente.direction)
    }
    @Test
    fun `Getfulname`(){
        val nombrecompleto = "Alex Carnero"
        Truth.assertThat(customer.getFullName()).isEqualTo(nombrecompleto)
    }
}