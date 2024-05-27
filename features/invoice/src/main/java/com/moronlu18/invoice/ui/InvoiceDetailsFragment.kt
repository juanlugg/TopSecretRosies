package com.moronlu18.invoice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.adapter.AdaptadorFacturas
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.usecase.InvoiceDetailsViewModel
import com.moronlu18.invoiceFragment.databinding.FilaArticulosBinding
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceDetailsBinding
import com.moronlu18.item.entity.item


class InvoiceDetailsFragment : Fragment() {

    lateinit var invoice: Invoice;
    lateinit var items: List<LineaItem>;
    lateinit var adapterLineaItem: AdaptadorArticulos
    private var _binding: FragmentInvoiceDetailsBinding? = null
    private var _bindingAr: FilaArticulosBinding? = null

    private val binding
        get() = _binding!!
    private val bindingAr
        get() = _bindingAr!!

    private val viewModel: InvoiceDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false)
        _bindingAr = FilaArticulosBinding.inflate(inflater, container, false)
        binding.rvInvoiceDetails.layoutManager = LinearLayoutManager(context)
        binding.viewmodel = this.viewModel

        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            invoice = it.getSerializable("invoice") as Invoice
            items = viewModel.getLineaItem(invoice.id.value)
            update()
            setup()
        }
    }

    private fun setup() {
        adapterLineaItem = AdaptadorArticulos(items, true) {
            Toast.makeText(
                requireContext(),
                "No puedes borrar un Articulo en la pestaña de detalles",
                Toast.LENGTH_LONG
            ).show()
        }
        with(binding.rvInvoiceDetails) {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapterLineaItem
        }
        binding.rvInvoiceDetails.adapter
        bindingAr.imgEliminarArticulo.visibility = View.GONE
        binding.rbInvoiceDetailsPendiente.isEnabled = false
        binding.rbInvoiceDetailsPagada.isEnabled = false
        binding.rbInvoiceDetailsPagadaVencida.isEnabled = false

    }

    private fun update() {
        var precios = items.map { it.precio * it.cantidad }
        binding.txtInvoiceDetailsNombreF.text = invoice.name
        binding.txtInvoiceDetailsNombre.text = viewModel.GetCliente(invoice.idCliente.value)?.nombre
        binding.txtInvoiceDetailsEmail.text = viewModel.GetCliente(invoice.idCliente.value)?.email?.value
        binding.txtInvoiceDetailsTelefono.text =
            viewModel.GetCliente(invoice.idCliente.value)?.telefono.toString()
        val posEmi = invoice.FeEmision.toString().indexOf('T')
        val posVen = invoice.FeVencimiento.toString().indexOf('T')
        binding.txtInvoiceDetailsFechaEmision.text =
            invoice.FeEmision.toString().substring(0, posEmi)
        binding.txtInvoiceDetailsFechaVencimiento.text =
            invoice.FeVencimiento.toString().substring(0, posVen)

        var SubTotal = precios.reduce { acc, ar -> acc + ar }
        binding.txtInvoiceDetailsSubtotal.text = String.format("%.2f €", SubTotal)
        binding.txtInvoiceDetailsImpuestos.text = "21 %"
        binding.txtInvoiceDetailsTotal.text =
            String.format("%.2f €", SubTotal + (SubTotal * 0.21))
        ComRadio(invoice.Estado)

    }


    fun ComRadio(s: InvoiceStatus) {
        when (s) {
            InvoiceStatus.Overdue -> {
                binding.rbInvoiceDetailsPagadaVencida.isChecked = true
            }

            InvoiceStatus.Paid -> {
                binding.rbInvoiceDetailsPagada.isChecked = true
            }

            InvoiceStatus.Pending -> {
                binding.rbInvoiceDetailsPendiente.isChecked = true
            }

            else -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}