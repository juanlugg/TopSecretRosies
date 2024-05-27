package com.moronlu18.customer.entity

import com.moronlu18.invoice.entity.UniqueId

data class CustomerId(
    override var value: Int
) : UniqueId(value)