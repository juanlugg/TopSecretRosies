package com.moronlu18.customerun.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customerun.databinding.FragmentCustomerDetailBinding

class CustomerDetailFragment : Fragment() {

    lateinit var customer: Customer

    private var _binding: FragmentCustomerDetailBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener(
            "key",
            this,
            FragmentResultListener { _, result ->
                customer =  result.getSerializable("customer") as Customer
                binding.txvnombreCustomerDetail.text = customer.nombre + " " + customer.apellidos
                binding.txvidCustomerDetail.text = customer.id.value.toString()
                binding.txvemailCustomerDetail.text = customer.email.value
                binding.txvciudadCustomerDetail.text = customer.city
                binding.txvdireccionCustomerDetail.text = customer.direction
                binding.txvtelefonoCustomerDetail.text = customer.telefono.toString()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

}