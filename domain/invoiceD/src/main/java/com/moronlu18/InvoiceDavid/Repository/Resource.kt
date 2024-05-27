package com.moronlu18.InvoiceDavid.Repository

sealed class Resource {
    data class Success<T>(var data: T?) :
        Resource()
    data class Error(var exception: Exception):Resource()
}