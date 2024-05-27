package com.moronlu18.invoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoiceFragment.databinding.FilaFacturasBinding


class AdaptadorFacturas(
    private val onClick: (fa: Invoice, n: Int) -> Unit,
    private val onDelete: (position: Int) -> Unit,
    private val getListItem: (id: Int) -> List<LineaItem>
) : ListAdapter<Invoice, AdaptadorFacturas.InvoiceHost>(INVOICE_COMPARATOR) {


    inner class InvoiceHost(var binding: FilaFacturasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(invoice: Invoice, listaItem: List<LineaItem>, position: Int) {
            with(binding) {
                if (listaItem.isNotEmpty()) {
                    var precios = listaItem.map { it.precio * it.cantidad }
                    var SubTotal = precios.reduce { acc, ar -> acc + ar }
                    txtLineaCliente.text = "Id: ${invoice.id.value}"
                    txtLineaTotal.text = String.format("%.2f €", SubTotal + (SubTotal * 0.21))
                    txtLineaNumArticulos.text = listaItem.map {
                        it.cantidad
                    }.reduce { acc, ar -> acc + ar }.toString()
                    val posEmi = invoice.FeEmision.toString().indexOf('T')
                    val posVen = invoice.FeVencimiento.toString().indexOf('T')
                    txtLineaFeEmision.text = invoice.FeEmision.toString().substring(0, posEmi)
                    txtLineaFeVencimiento.text =
                        invoice.FeVencimiento.toString().substring(0, posVen)
                    cvFactura.setOnClickListener {
                        onClick(invoice, 0)
                    }
                    imgEliminar.setOnClickListener {
                        onDelete(position)
                    }
                    imgEditar.setOnClickListener {
                        onClick(invoice, 1)
                    }

                }

            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvoiceHost {

        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceHost(FilaFacturasBinding.inflate(layoutInflater, parent, false))
    }

    fun sortId() {
        val sortedInvoiceList = currentList.sortedBy { it.id.value }
        submitList(sortedInvoiceList)
    }

    fun sortIdCliente() {
        val sortedInvoiceList = currentList.sortedBy { it.idCliente.value }
        submitList(sortedInvoiceList)
    }

    override fun onBindViewHolder(holder: InvoiceHost, position: Int) {
        val f = getItem(position)

        holder.bind(f, getListItem(f.id.value), position)


    }

    companion object {
        private val INVOICE_COMPARATOR = object : DiffUtil.ItemCallback<Invoice>() {
            override fun areItemsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}

