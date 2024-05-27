package com.moronlu18.invoice.ui.utils.calendar

import androidx.fragment.app.FragmentManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarInvoice {
    fun showDatePickerDialog(manager: FragmentManager, listener: (day: Int, month : Int, year: Int) -> Unit, ){
        val datePicker = DatePickerFragment{day: Int, month : Int, year: Int -> listener(day,month,year)}
        datePicker.show(manager, "datePicker")
    }
    companion object{
        /**
         * Función que devuelve la fecha actual y formateada
         */
        fun getCurrentDate(): String {
            return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        }//Implementarlo más tarde para que todos puedan usarlo
    }
}