package com.moronlu18.customerun.ui

sealed class CustomerState {
    data object EmailEmtyError : CustomerState()
    data object EmailFormatError : CustomerState()
    data object NombreEmtyError : CustomerState()
    data object Success : CustomerState()


}