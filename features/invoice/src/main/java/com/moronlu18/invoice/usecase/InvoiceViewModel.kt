package com.moronlu18.invoice.usecase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.ui.InvoiceState
import com.moronlu18.item.repository.ItemRepository
import kotlinx.coroutines.launch
import java.time.Instant

class InvoiceViewModel : ViewModel() {
    var articulos: MutableList<LineaItem> = ArrayList<LineaItem>()
    var editar: Boolean = false
    var name = MutableLiveData<String>()
    var idFactura = MutableLiveData<String>()
    var idCliente = MutableLiveData<String>()
    var FeEmi = MutableLiveData<String>()
    var FeVen = MutableLiveData<String>()
    var nombreCliente = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var telefono = MutableLiveData<String>()
    val clientes = CustomerRepository.getCustomerListRAW()
    private var _customer: Customer? = null
    val _facturas = InvoiceRepository.getInvoiceListRAW()
    val RawArticulos = ItemRepository.getItemListRAW()
    var updateListItem : MutableList<LineaItem> = ArrayList<LineaItem>()

    val facturas
        get() = _facturas!!

    val cliente
        get() = _customer!!


    //var email = MutableLiveData<String>()
    //var password = MutableLiveData<String>()
    private var state = MutableLiveData<InvoiceState>()


    fun setLista(listaArticulos: MutableList<LineaItem>) {
        articulos = listaArticulos;

    }

    fun getLineaItem(id: Int): MutableList<LineaItem> {
        return InvoiceRepository.getLineaItemList(id).toMutableList()
    }


    fun validate() {
        when {
            TextUtils.isEmpty(name.value) -> state.value = InvoiceState.nameEmtyError
            TextUtils.isEmpty(idFactura.value) -> state.value = InvoiceState.idFacturaEmtyError
            TextUtils.isEmpty(idCliente.value) -> state.value = InvoiceState.idClienteEmtyError
            TextUtils.isEmpty(FeEmi.value) -> state.value = InvoiceState.feEmiEmtyError
            TextUtils.isEmpty(FeVen.value) -> state.value = InvoiceState.feVenEmtyError
            nuevoId(idFactura.value) && editar -> state.value = InvoiceState.facturaNewIdError
            !validarIdFactura(idFactura.value) && !editar -> state.value =
                InvoiceState.facturaValidateError
            !introduceCliente() -> state.value = InvoiceState.idClienteInvalidError
            !ValidateFecha(FeEmi.value) -> state.value = InvoiceState.feEmiInvalidError
            !ValidateFecha(FeVen.value) -> state.value = InvoiceState.feVenInvalidError
            !olderDate(SetDate(FeEmi.value!!)!!, SetDate(FeVen.value!!)!!) -> state.value =
                InvoiceState.dateInvalidError
            articulos.size == 0 -> state.value = InvoiceState.ArticulosEmptyError

            else -> {
                CrearFactura(editar)

                state.value = InvoiceState.Success
            }

        }
    }

    fun idInvoice(): Int? {
        try {
            if (idFactura != null) {
                return idFactura.value!!.toInt()
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }

    }
    fun InsertLineaItem(lineaItem:LineaItem ){
        val result = InvoiceRepository.insertLineaItem(lineaItem)
        Log.d("Resultado Insertar LineaItem", result.toString())
    }
    fun UpdateLineaItem(lineaItem: LineaItem) {
        updateListItem.add(lineaItem)

    }

    fun CrearFactura(editar: Boolean) {
        if (editar) {
            InvoiceRepository.updateInvoice(
                Invoice(
                    InvoiceId(idFactura.value!!.toInt()),
                    cliente.id,
                    SetFecha(FeEmi.value!!),
                    SetFecha(FeVen.value!!),
                    InvoiceStatus.Pending,
                    name.value!!
                )
            )
            updateListItem.forEach {
                InvoiceRepository.updateLineaItem(it)
            }
            //val result = InvoiceRepository.insertLineaItems(articulosNuevos)
            //Log.d("Resultado Insertar LineaItem", result.toString())
        } else {
            InvoiceRepository.insertInvoice(
                Invoice(
                    InvoiceId(idFactura.value!!.toInt()),
                    cliente.id,
                    SetFecha(FeEmi.value!!),
                    SetFecha(FeVen.value!!),
                    InvoiceStatus.Pending,
                    name.value!!
                )
            )
            val result = InvoiceRepository.insertListLineaItems(articulos)
            Log.d("Resultado Insertar LineaItem", result.toString())

        }

    }

    private fun SetFecha(fecha: String): Instant {
        val dateString = fecha + "T00:00:00Z"
        val instant = Instant.parse(dateString)
        return instant


    }

    private fun olderDate(t1: Instant, t2: Instant): Boolean {
        if (t1 != null && t2 != null) {
            return t1.isBefore(t2)
        } else {
            return false
        }

    }

    fun nuevoId(cadena: String?): Boolean {
        try {
            val i = cadena?.toInt()
            facturas.forEach {
                if (it.id.value == i) {
                    return false
                }
            }
        } catch (e: Exception) {
            return true
        }
        return true
    }

    fun validarIdFactura(cadena: String?): Boolean {
        try {
            val i = cadena?.toInt()
            facturas.forEach {
                if (it.id.value == i) {
                    return false
                }
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun SetDate(fecha: String): Instant? {
        val dateString = fecha + "T00:00:00Z"
        val instant = Instant.parse(dateString)
        return instant


    }

    private fun ValidateFecha(s: String?): Boolean {
        try {
            if (s != null) {
                val dateString = s + "T00:00:00Z"
                Instant.parse(dateString)
            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun introduceCliente(): Boolean {
        try {
            var i: Int = idCliente.value!!.toInt()
            if (i != null) {
                clientes.forEach {
                    if (it.id.value == i) {
                        _customer = it
                        this.nombreCliente.value = it.nombre
                        this.email.value = it.email.value
                        this.telefono.value = it.telefono
                        return true
                    }
                }
            }
        } catch (e: Exception) {
            this.nombreCliente.value = ""
            this.email.value = ""
            this.telefono.value = ""
            return false
        }
        return false
    }

    fun getState(): LiveData<InvoiceState> {
        return state;
    }

    fun deleteLineaItem(lineaItem: LineaItem) {
        InvoiceRepository.deleteLineaItem(lineaItem)
    }


}