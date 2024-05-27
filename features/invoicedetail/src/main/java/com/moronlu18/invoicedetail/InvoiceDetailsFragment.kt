package com.moronlu18.invoicedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.invoicedetail.databinding.FragmentInvoiceDetailsBinding

data class Articulo(val nombre:String,val precio:Double)
class InvoiceDetailsFragment : Fragment() {

    private var _binding: FragmentInvoiceDetailsBinding? = null
    private val binding
        get() = _binding!!

    val articulos = listOf<Articulo>(
        Articulo("Mesa",222.2),
        Articulo("Portaminas",1.0)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false)

        binding.rvInvoiceDetails.adapter = AdaptadorArticulos(articulos)
        binding.rvInvoiceDetails.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment
        return binding.root
    }

}