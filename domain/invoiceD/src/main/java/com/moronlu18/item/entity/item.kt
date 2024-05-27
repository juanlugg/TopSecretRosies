 package com.moronlu18.item.entity

 import androidx.annotation.NonNull
 import androidx.room.Entity
 import androidx.room.PrimaryKey
 import androidx.room.TypeConverters
 import com.moronlu18.invoice.converter.ItemIdTypeConverter
 import com.moronlu18.invoice.converter.ItemTaxableBoolConverter
 import com.moronlu18.invoice.converter.ItemTypeConverter

 /**
 * Al utilizar data class se implementa de forma automatica el metodo equals toString, hashcode, copy
 * teniendo en cuenta las propiedades declarasas en el constructor primario
 */
@Entity(tableName = "Item")
data class item(
    @PrimaryKey
    @TypeConverters(ItemIdTypeConverter::class)
    val id: ItemId,
    @NonNull
    var name: String,
    @NonNull
    var rate: Double,
    @TypeConverters(ItemTypeConverter::class)
    var type: itemType,
    @NonNull
    var description: String,
    @NonNull
    @TypeConverters(ItemTaxableBoolConverter::class)
    var isTaxable: Boolean,
    @NonNull
    var Iva: Double
) : Comparable<item>{
    override fun compareTo(other: item): Int {
        return name.compareTo(other.name)
    }

    override fun toString(): String {
        return String.format("%s - %.2f€", name , rate)
    }
}
enum class itemType {
    PRODUCT,
    SERVICE
}