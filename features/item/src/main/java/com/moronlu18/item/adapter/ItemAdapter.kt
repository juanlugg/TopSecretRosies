package com.moronlu18.item.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.item.entity.item
import com.moronlu18.itemcreation.R

class ItemAdapter(private var itemList: List<item>, private val onItemClick: (item) -> Unit, private val onDeleteClick: (item) -> Unit, private val onEditClick: (item) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(item)
        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItemList(updatedList: List<item>) {
        itemList = updatedList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item:item) {
            itemView.findViewById<TextView>(R.id.tvIdCard).text = "${item.id.value}"
            itemView.findViewById<TextView>(R.id.tvNameCard).text = "${item.name}"
            itemView.findViewById<TextView>(R.id.tvRateCard).text = String.format("%.2f", item.rate)
            itemView.findViewById<TextView>(R.id.tvTypeCard).text = "${item.type}"
            itemView.findViewById<TextView>(R.id.tvDescriptionCard).text = "${item.description}"
            itemView.findViewById<TextView>(R.id.tvIsTaxable).text = "${item.isTaxable}"
        }



        init {
            itemView.findViewById<ImageView>(R.id.ivDelete).setOnClickListener {
                onDeleteClick.invoke(itemList[adapterPosition])
            }

            itemView.findViewById<ImageView>(R.id.ivEdit).setOnClickListener {
                onEditClick.invoke(itemList[adapterPosition])
            }

        }
    }
}