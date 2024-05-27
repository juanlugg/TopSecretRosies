package com.moronlu18.Invoice

import com.google.common.truth.Truth
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.invoice.entity.Invoice
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.time.Instant

class InvoiceTest {
    private fun SetDate(fecha: String): Instant {
        val dateString = fecha + "T00:00:00Z"
        val instant = Instant.parse(dateString)
        return instant


    }
    @Test
    fun testInvoiceCreation() {
        val invoice = Invoice(
            InvoiceId(4), CustomerId(2), SetDate("2000-01-01"), SetDate("2200-01-01"), InvoiceStatus.Pending,"2020DD"
        )
        val idInvoice = InvoiceId(4);
        val idCustomer = CustomerId(2);
        val fechaEmi = SetDate("2000-01-01")
        val fechaVen = SetDate("2200-01-01")
        val status = InvoiceStatus.Pending
        val name = "2020DD"
        assertTrue(invoice != null)
        Truth.assertThat(idInvoice).isEqualTo(invoice.id)
        Truth.assertThat(idCustomer).isEqualTo(invoice.idCliente)
        Truth.assertThat(fechaEmi).isEqualTo(invoice.FeEmision)
        Truth.assertThat(fechaVen).isEqualTo(invoice.FeVencimiento)
        Truth.assertThat(status).isEqualTo(invoice.Estado)
        Truth.assertThat(name).isEqualTo(invoice.name)
    }

}