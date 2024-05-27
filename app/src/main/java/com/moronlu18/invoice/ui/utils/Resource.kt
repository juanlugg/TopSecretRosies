package com.moronlu18.invoice.ui.utils

sealed class Resource {
    data class Success<T>(var data: T?) :
        Resource()
    data class Error(var exception: Exception):Resource()
}