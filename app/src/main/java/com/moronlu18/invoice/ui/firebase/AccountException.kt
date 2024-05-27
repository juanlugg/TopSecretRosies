package com.moronlu18.invoice.ui.firebase

sealed class AccountException(message: String = ""): RuntimeException(message) {
    class InvalidEmailFormat: AccountException("Email con formato inválido")
}