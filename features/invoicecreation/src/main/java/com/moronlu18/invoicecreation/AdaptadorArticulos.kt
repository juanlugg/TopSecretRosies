package com.moronlu18.invoicecreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorArticulos(val articulos:List<Articulo>)
    : RecyclerView.Adapter<AdaptadorArticulos.ViewHolder>() {

    class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        var nombre: TextView
        var precio: TextView

        init {
            nombre = v.findViewById(R.id.tvInvoiceListaArticulosNombre)
            precio = v.findViewById(R.id.tvInvoiceListaArticulosPrecio)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorArticulos.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fila_articulos,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdaptadorArticulos.ViewHolder, position: Int) {
        val f = articulos[position]
        holder.nombre.text = f.nombre
        holder.precio.text = f.precio.toString()

    }

    override fun getItemCount(): Int {
        return articulos.size
    }
}