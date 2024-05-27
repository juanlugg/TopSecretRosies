package com.moronlu18.customerun.ui

open class CustomerListState {
    data object noDataError : CustomerListState()

    data object Success : CustomerListState()
}