package com.moronlu18.invoice.utils.calendar

import com.google.common.truth.Truth
import com.moronlu18.invoice.ui.utils.calendar.CalendarInvoice
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarInvoiceTest {
    @Test
    fun `getCurrentDate correct`(){
        Truth.assertThat(CalendarInvoice.getCurrentDate()).isEqualTo(
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                Date()
            ))
    }
}
