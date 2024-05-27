package com.moronlu18.Invoice

import com.google.common.truth.Truth
import com.moronlu18.InvoiceDavid.entity.LineaItem
import junit.framework.TestCase
import junit.framework.TestCase.assertTrue
import org.junit.Test

class lineaItem {
    val lineaItem = LineaItem(
        1, 2, 3, 2.2, 20.0
    )

    @Test
    fun testLineaItem_idInvoice() {
        val idInvoice = 2;

        Truth.assertThat(idInvoice).isEqualTo(lineaItem.id_invoice)

    }

    @Test
    fun testLineaItem_idItem() {

        val idCustomer = 1;

        Truth.assertThat(idCustomer).isEqualTo(lineaItem.id_item)

    }

    @Test
    fun testLineaItem_cantidad() {

        val cantidad = 3

        Truth.assertThat(cantidad).isEqualTo(lineaItem.cantidad)

    }

    @Test
    fun testLineaItem_precio() {

        val precio = 2.2

        Truth.assertThat(precio).isEqualTo(lineaItem.precio)

    }

    @Test
    fun testLineaItem_iva() {
        val iva = 20.0
        Truth.assertThat(iva).isEqualTo(lineaItem.iva)
    }
    @Test
    fun testLineaItem_equals() {
        val lineaItem2 = LineaItem(
            1, 2, 3, 2.2, 20.0
        )
        assertTrue(lineaItem.equals(lineaItem2))
    }
    /*@Test
    fun testLineaItem_getName() {
        val name = "zanahoria"

        Truth.assertThat(name).isEqualTo(LineaItem.getName(1))
    }*/


}