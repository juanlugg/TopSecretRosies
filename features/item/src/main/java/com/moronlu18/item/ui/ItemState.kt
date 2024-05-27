package com.moronlu18.item.ui

sealed class ItemState {
    data class NameEmptyError(val message: String) : ItemState()
    data class RateFormatError(val message: String) : ItemState()
    object RequiredDataMissing : ItemState()
    object TaxableItem : ItemState()
}