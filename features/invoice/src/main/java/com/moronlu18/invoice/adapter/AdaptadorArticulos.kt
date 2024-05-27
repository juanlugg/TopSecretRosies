package com.moronlu18.invoice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoiceFragment.R
import com.moronlu18.item.entity.item


class AdaptadorArticulos(
    val articulos: List<LineaItem>,
    val mostrar : Boolean,
    private val onDelete: (position: Int) -> Unit,
) : RecyclerView.Adapter<AdaptadorArticulos.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var nombre: TextView
        var precio: TextView
        var cantidad: TextView
        var img: ImageView
        init {
            nombre = v.findViewById(R.id.tvInvoiceListaArticulosNombre)
            precio = v.findViewById(R.id.tvInvoiceListaArticulosPrecio)
            cantidad = v.findViewById(R.id.tvCantidad)
            img = v.findViewById(R.id.imgEliminarArticulo)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): AdaptadorArticulos.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fila_articulos, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdaptadorArticulos.ViewHolder, position: Int) {
        val f = articulos[position]

        holder.nombre.text = LineaItem.getName(f.id_item)
        holder.cantidad.text = f.cantidad.toString()
        holder.precio.text = String.format("%.2f€", f.precio)
        if(mostrar){
            holder.img.visibility = View.GONE
        }else{
            holder.img.visibility = View.VISIBLE
        }
        holder.img.setOnClickListener{
            onDelete(position)
        }

    }

    override fun getItemCount(): Int {
        return articulos.size
    }
}