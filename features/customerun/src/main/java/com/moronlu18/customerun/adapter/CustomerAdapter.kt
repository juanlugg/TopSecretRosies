package com.moronlu18.customerun.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customerun.databinding.FilaCustomerBinding

class CustomerAdapter(
    private val onClick: (c: Customer, n: Int) -> Unit,
    private val onDelete: (position: Int) -> Unit
):
ListAdapter<Customer, CustomerAdapter.CustomerHost>(CUSTOMER_COMPARATOR){
    inner class  CustomerHost(var binding: FilaCustomerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(customer:Customer){
            with(binding){
                txtnombreCustomerList.text=customer.nombre
                txtapellidosCustomerList.text=customer.apellidos
                txtemailCustomerList.text=customer.email.value
                btndelete.setOnClickListener {
                    onDelete(position)
                }
                cdvCusotmerList.setOnClickListener {
                    onClick(customer,0)
                }
                btnedit.setOnClickListener {
                    onClick(customer,1)
                }
            }

        }
    }
    fun notifyChanged() {
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: CustomerHost, position: Int) {
        //Se accede a un elemento de la lista interna de adapterRv mendiante el método getItem(position)
        //Se accede a la lista interna midiante currentList

        var u = getItem(position)

        holder.bind(u)
    }

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):CustomerHost{
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomerHost(FilaCustomerBinding.inflate(layoutInflater,parent,false))
    }
    fun sortId() {
        val customersort = currentList.sortedBy { it.id.value }
        submitList(customersort)
    }
    fun sortbyName() {
        val customersort = currentList.sortedBy { it.nombre }
        submitList(customersort)
    }
    companion object {
        private val CUSTOMER_COMPARATOR = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.nombre == newItem.nombre
            }

        }
    }
}