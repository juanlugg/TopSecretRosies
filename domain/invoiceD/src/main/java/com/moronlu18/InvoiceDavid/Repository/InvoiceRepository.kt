package com.moronlu18.InvoiceDavid.Repository

import android.database.sqlite.SQLiteException
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.invoice.entity.Invoice
import kotlinx.coroutines.flow.Flow

class InvoiceRepository {
    companion object {
        fun insertInvoice(fa: Invoice): Resource {
            return try {
                InvoiceDatabase.getInstance().invoiceDao().insert(fa)
                Resource.Success<Invoice>(fa)
            } catch (e: SQLiteException) {
                println(e.message)
                Resource.Error(e)
            }
        }

        fun updateInvoice(fa: Invoice): Resource {
            return try {
                InvoiceDatabase.getInstance().invoiceDao().update(fa)
                Resource.Success<Invoice>(fa)
            } catch (e: SQLiteException) {
                println(e.message)
                Resource.Error(e)
            }
        }

        fun insertListLineaItems(lista: MutableList<LineaItem>): Resource {
            return try {
                lista.forEach { nuevo ->
                   InvoiceDatabase.getInstance().lineaItemDao().insert(nuevo)
                }

                Resource.Success<MutableList<LineaItem>>(lista)
            } catch (e: SQLiteException) {
                println(e.message)
                Resource.Error(e)
            }
        }

        fun insertLineaItem(item: LineaItem): Resource {
            return try {
                InvoiceDatabase.getInstance().lineaItemDao().insert(item)
                Resource.Success<LineaItem>(item)
            } catch (e: SQLiteException) {
                println(e.message)
                Resource.Error(e)
            }
        }

        fun updateLineaItem(lineaItem: LineaItem) {
            InvoiceDatabase.getInstance().lineaItemDao().update(lineaItem)
        }

        fun deleteInvoice(invoice: Invoice) {
            InvoiceDatabase.getInstance().invoiceDao().delete(invoice)
        }

        fun deleteLineaItems(lista: List<LineaItem>) {
            try {
                lista.forEach {
                    InvoiceDatabase.getInstance().lineaItemDao().delete(it)
                }

            } catch (e: SQLiteException) {
                println(e.message)
            }
        }


        fun getInvoiceList(): Flow<List<Invoice>> {
            return InvoiceDatabase.getInstance().invoiceDao().selectAll()
            }
        fun getInvoiceListOrderByIdCliente(): Flow<List<Invoice>> {
            return InvoiceDatabase.getInstance().invoiceDao().selectAllOrderByIdCliente()


        }

        fun getInvoiceListRAW(): List<Invoice> {
            return InvoiceDatabase.getInstance().invoiceDao().selectAllRAW()
        }


        fun getLineaItemList(id: Int): List<LineaItem> {
            return InvoiceDatabase.getInstance().lineaItemDao().selectFromInvoice(id)
        }

        fun deleteLineaItem(lineaItem: LineaItem) {
            InvoiceDatabase.getInstance().lineaItemDao().delete(lineaItem)
        }


    }


}