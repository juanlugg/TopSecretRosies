package com.moronlu18.invoice.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.item.repository.ItemRepository
import java.time.Instant

class ProviderInvoice private constructor() {


    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        val datasetFactura: MutableList<Invoice> = setUpDataSetFactura()


        @RequiresApi(Build.VERSION_CODES.O)
        private fun SetFecha(fecha: String): Instant {
            val dateString = fecha + "T00:00:00Z"
            //val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            //val localDateTime = LocalDateTime.parse(dateString, formatter)
            val instant = Instant.parse(dateString)
            //return localDateTime.toInstant(ZoneOffset.MAX)
            return instant
        }

        //@RequiresApi(Build.VERSION_CODES.O)
        @RequiresApi(Build.VERSION_CODES.O)
        private fun setUpDataSetFactura(): MutableList<Invoice> {
            var dataset: MutableList<Invoice> = ArrayList()

            val articulos = ItemRepository.getItemList()
            /* dataset.add(
                 Invoice(
                     InvoiceId(1),
                     1, SetFecha("2020-10-20"), SetFecha("2021-01-20"), mutableListOf(
                         //Articulo("Zapato", 20.2),
                         //Articulo("Cordón", 2.2)
                         LineaItem(articulos[0].id.value, 1, 1, articulos[0].rate, articulos[0].Iva),
                         LineaItem(articulos[1].id.value, 1, 1, articulos[1].rate, articulos[1].Iva),

                         ),
                     InvoiceStatus.Pending
                 )
             )
             dataset.add(
                 Invoice(
                     InvoiceId(2),
                     1, SetFecha("2010-10-02"), SetFecha("2019-10-23"), mutableListOf(
                         LineaItem(articulos[0].id.value, 1, 1, articulos[0].rate, articulos[0].Iva),
                         LineaItem(articulos[1].id.value, 1, 1, articulos[1].rate, articulos[1].Iva),
                     ),
                     InvoiceStatus.Pending
                 )
             )*/
            return dataset
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun CreateInvoice(
            idFactura: Int,
            cliente: Int,
            feEmi: Instant,
            feVen: Instant,
            name: String,
            status: InvoiceStatus
        ) {
            var f = Invoice(
                InvoiceId(idFactura),
                CustomerId(cliente),
                feEmi,
                feVen,
                status,
                name
            )
            //println(f.CantidadArticulos())
            ProviderInvoice.datasetFactura.add(f)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun editInvoice(
            idFactura: Int,
            cliente: Int,
            feEmi: Instant,
            feVen: Instant,
            name: String,
            status: InvoiceStatus
        ) {
            datasetFactura.remove(
                getInvoice(
                    idFactura
                )
            )
            var f = Invoice(
                InvoiceId(idFactura),
                CustomerId(cliente),
                feEmi,
                feVen,
                status,
                name
            )
            datasetFactura.add(f)
        }

        private fun getInvoice(id: Int): Invoice? {

            datasetFactura.forEach {
                if (id == it.id.value) {

                    return it
                }
            }
            return null;
        }

    }
}