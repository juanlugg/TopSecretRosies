package com.moronlu18.invoice.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.ui.utils.calendar.CalendarInvoice
import com.moronlu18.invoice.usecase.InvoiceViewModel
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceCreationBinding
import com.moronlu18.item.entity.item


//data class Articulo(val nombre:String,val precio:Double)
class InvoiceCreationFragment : Fragment() {
    lateinit var invoice: Invoice
    lateinit var items: MutableList<LineaItem>;

    private var editar = false
    lateinit var adapterLineaItem: AdaptadorArticulos
    private var calendar = CalendarInvoice()
    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding
        get() = _binding!!


    private val viewModel: InvoiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    inner class textWatcher(var t: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            viewModel.introduceCliente()
            t.isErrorEnabled = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val itemListString = articulos.map { articulo -> articulo.nombre }
        val adaptersp =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, viewModel.RawArticulos)
        setup()
        adaptersp.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spArticulo.adapter = adaptersp
        arguments?.let {
            editar = it.getBoolean("editar")
            if (editar) {
                viewModel.editar = editar
                invoice = it.getSerializable("invoice") as Invoice
                items = viewModel.getLineaItem(invoice.id.value)
                setup()
                update()
            }

        }

        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                InvoiceState.nameEmtyError -> {
                    binding.tilNameInvoiceCreationIdFactura.error = "Introduce un Nombre"
                    binding.tilNameInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.idClienteEmtyError -> {
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de Cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                InvoiceState.idFacturaEmtyError -> {
                    binding.tilInvoiceCreationIdFactura.error = "Introduce un id para la factura"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.feVenEmtyError -> {
                    binding.tilInvoiceCreationFeVen.error =
                        "Introduce una fecha con formato YYYY/MM/DD"
                    binding.tilInvoiceCreationFeVen.requestFocus()
                }

                InvoiceState.feEmiEmtyError -> {
                    binding.tilInvoiceFeEmi.error = "Introduce una fecha con formato YYYY/MM/DD"
                    binding.tilInvoiceFeEmi.requestFocus()
                }

                InvoiceState.facturaValidateError -> {
                    binding.tilInvoiceCreationIdFactura.error =
                        "Id de la factura invalido, debe ser un id no existente"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.facturaNewIdError -> {
                    binding.tilInvoiceCreationIdFactura.error =
                        "Id de la factura invalido, para editar el id debe existir"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.idClienteInvalidError -> {
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                InvoiceState.feVenInvalidError -> {
                    binding.tilInvoiceCreationFeVen.error =
                        "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceCreationFeVen.requestFocus()
                }

                InvoiceState.feEmiInvalidError -> {
                    binding.tilInvoiceFeEmi.error = "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceFeEmi.requestFocus()
                }

                InvoiceState.dateInvalidError -> {
                    println("Erroe en la fecha")
                    Toast.makeText(
                        requireContext(),
                        "La fecha de vencimiento debe ser mayor que la fecha de Emisión",
                        Toast.LENGTH_LONG
                    ).show()
                }

                InvoiceState.ArticulosEmptyError -> Toast.makeText(
                    requireContext(), "Introduce algún artículo", Toast.LENGTH_SHORT
                ).show()

                else -> Created()
            }


        }

        binding.tieInvoiceFeEmi.setOnClickListener {
            calendar.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieInvoiceFeEmi.setText(
                    String.format(
                        "%04d-%02d-%02d", year, month + 1, day
                    )
                )
            }
        }
        binding.tieInvoiceCreationFeVen.setOnClickListener {
            calendar.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieInvoiceCreationFeVen.setText(
                    String.format(
                        "%04d-%02d-%02d", year, month + 1, day
                    )
                )
            }
        }

        binding.rvInvoiceArticulos.adapter = adapterLineaItem
        binding.rvInvoiceArticulos.layoutManager = LinearLayoutManager(context)
        binding.spArticulo.adapter = adaptersp

        var cantidad = 1
        binding.btnArticulos.setOnClickListener {
            if (viewModel.idInvoice() != null) {
                val b: String = binding.spArticulo.selectedItem.toString()
                val datos = b.split('-')
                val a = ObtenerItem(datos[0])
                var insert = true;

                viewModel.articulos.forEach {
                    if (it.id_item == a!!.id.value) {
                        cantidad = ++it.cantidad;
                        insert = false;
                    }
                }
                val lineaItem = LineaItem(
                    a!!.id.value, viewModel.idInvoice()!!, cantidad, a.rate, a.Iva
                )
                if (insert) {
                    if (viewModel.editar) {
                        viewModel.UpdateLineaItem(lineaItem)
                    }
                    viewModel.articulos.add(
                        lineaItem
                    )
                } else {
                    viewModel.UpdateLineaItem(lineaItem)
                }


                adapterLineaItem.notifyDataSetChanged()
                binding.rvInvoiceArticulos.scrollToPosition(viewModel.articulos.size - 1)

                updatePrecios()

            } else {
                Toast.makeText(requireContext(), "Introduzca un id válido", Toast.LENGTH_LONG)
                    .show()
            }

        }

        binding.btnCrear.setOnClickListener {
            viewModel.validate()
        }

        binding.tieInvoiceCreationIdCliente.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationIdCliente
            )
        )
        binding.tieInvoiceCreationIdFactura.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationIdFactura
            )
        )
        binding.tieInvoiceFeEmi.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceFeEmi
            )
        )
        binding.tieInvoiceCreationFeVen.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationFeVen
            )
        )


    }

    private fun setup() {
        if (editar) {
            viewModel.setLista(items)
        }
        adapterLineaItem = AdaptadorArticulos(viewModel.articulos, false) { i: Int ->
            viewModel.deleteLineaItem(viewModel.articulos[i])
            viewModel.articulos.removeAt(i)
            //notifyItemRemoved(position)
            binding.rvInvoiceArticulos.adapter?.notifyDataSetChanged()
            updatePrecios()
        }
        with(binding.rvInvoiceArticulos) {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapterLineaItem
        }
    }

    private fun update() {
        val precios = items.map { it.precio * it.cantidad }
        viewModel.name.value = invoice.name
        viewModel.idFactura.value = invoice.id.value.toString()
        viewModel.idCliente.value = invoice.idCliente.value.toString()
        //rellenarCliente(binding.tieInvoiceCreationIdCliente.text)
        viewModel.introduceCliente()

        val posEmi = invoice.FeEmision.toString().indexOf('T')
        val posVen = invoice.FeVencimiento.toString().indexOf('T')
        viewModel.FeEmi.value = invoice.FeEmision.toString().substring(0, posEmi)

        viewModel.FeVen.value = invoice.FeVencimiento.toString().substring(0, posVen)

        val SubTotal = precios.reduce { acc, ar -> acc + ar }
        binding.txtInvoiceCreationSubtotal.text = String.format("%.2f €", SubTotal)
        binding.txtInvoiceCreationTotal.text = String.format("%.2f €", SubTotal + (SubTotal * 0.21))

        binding.tieInvoiceCreationIdCliente.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationIdCliente
            )
        )
        binding.tieInvoiceCreationIdFactura.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationIdFactura
            )
        )

    }

    private fun updatePrecios() {

        if (viewModel.articulos.size < 1) {
            binding.txtInvoiceCreationSubtotal.text = ""
            binding.txtInvoiceCreationTotal.text = ""
        } else {
            val precios = viewModel.articulos.map { it.precio * it.cantidad }
            val SubTotal = precios.reduce { acc, ar -> acc + ar }
            binding.txtInvoiceCreationSubtotal.text = String.format("%.2f €", SubTotal)
            binding.txtInvoiceCreationTotal.text =
                String.format("%.2f €", SubTotal + (SubTotal * 0.21))
        }


    }


    fun Created() {
        findNavController().popBackStack()
    }

    fun ObtenerItem(nombre: String): item? {
        viewModel.RawArticulos.forEach {
            if (nombre.trim() == it.name) {

                return it
            }
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}