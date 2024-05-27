package com.moronlu18.invoice.entity

open class UniqueId(open val value :Any){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UniqueId) return false

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
