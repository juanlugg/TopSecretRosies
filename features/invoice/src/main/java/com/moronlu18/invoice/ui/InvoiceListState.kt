package com.moronlu18.invoice.ui

sealed class InvoiceListState {
    data object noDataError : InvoiceListState()

    data object Success : InvoiceListState()

}