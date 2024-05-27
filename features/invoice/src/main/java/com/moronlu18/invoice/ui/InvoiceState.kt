package com.moronlu18.invoice.ui



sealed class InvoiceState {
    data object idFacturaEmtyError : InvoiceState()
    data object nameEmtyError : InvoiceState()
    data object idClienteEmtyError : InvoiceState()
    data object idClienteInvalidError : InvoiceState()
    data object feEmiInvalidError : InvoiceState()
    data object facturaNewIdError : InvoiceState()
    data object facturaValidateError : InvoiceState()

    data object ArticulosEmptyError : InvoiceState()

    data object feVenInvalidError : InvoiceState()
    data object feEmiEmtyError : InvoiceState()
    data object feVenEmtyError : InvoiceState()

    data object dateInvalidError : InvoiceState()

    data object Success : InvoiceState()



}