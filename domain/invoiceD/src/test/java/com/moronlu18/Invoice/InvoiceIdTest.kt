package com.moronlu18.Invoice

import com.google.common.truth.Truth
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.customer.entity.CustomerId
import org.junit.Test

class InvoiceIdTest {
    @Test
    fun `A es igual a B`() {
        //Configuración
        val a = InvoiceId(2)
        val b = InvoiceId(2)
        //assert al objeto
        //Valor que le doy, valor esperado.
        Truth.assertThat(a).isEqualTo(b)
        //   Truth.assertThat(a).isInstance
    }

    @Test
    fun `A es diferente a B`() {
        //Configuración
        val a = InvoiceId(3)
        val b = InvoiceId(2)

        Truth.assertThat(a).isNotEqualTo(b)
    }

    @Test
    fun `AccountId es igual a una instancia del mismo tipo`() {
        val a = InvoiceId(2)

        Truth.assertThat(a).isInstanceOf(InvoiceId::class.java)
    }

    @Test
    fun `AccountId no es igual a una instancia de otro tipo`() {
        val a = InvoiceId(2)

        Truth.assertThat(a).isNotInstanceOf(CustomerId::class.java)
    }


}