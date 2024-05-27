package com.moronlu18.invoice.usecase

import androidx.lifecycle.ViewModel
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.invoice.Repository.ProviderInvoice

class InvoiceDetailsViewModel:ViewModel() {
    val _facturas = ProviderInvoice.datasetFactura
    val facturas
        get() = _facturas!!

    fun GetCliente(id:Int): Customer? {
        return CustomerRepository.getCliente(id)
    }
    fun getLineaItem(id:Int):List<LineaItem>{
        return InvoiceRepository.getLineaItemList(id)
    }
}