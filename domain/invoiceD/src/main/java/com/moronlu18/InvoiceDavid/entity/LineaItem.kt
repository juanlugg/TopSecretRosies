package com.moronlu18.InvoiceDavid.entity

import android.content.ClipData.Item
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.repository.ItemRepository
import java.io.Serializable

@Entity(
    tableName = "LineaItem", foreignKeys = [ForeignKey(
        entity = Invoice::class,
        parentColumns = arrayOf("id"), childColumns = arrayOf("id_invoice"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
    ), ForeignKey(
        entity = item::class,
        parentColumns = arrayOf("id"), childColumns = arrayOf("id_item"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE
    )], primaryKeys = ["id_item", "id_invoice"]
)

data class LineaItem(
    val id_item: Int,
    val id_invoice: Int,
    var cantidad: Int,
    val precio: Double,
    val iva: Double
) :  Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LineaItem) return false
        return id_invoice == other.id_invoice && id_item == other.id_item
    }

    companion object {
        fun getName(id: Int): String? {
            return ItemRepository.getName(ItemId(id))
        }

    }

}