package com.moronlu18.InvoiceDavid.entity

import com.moronlu18.invoice.entity.UniqueId

data class InvoiceId(
    override var value: Int
) : UniqueId(value)