package com.moronlu18.InvoiceDavid.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moronlu18.invoice.entity.Invoice
import kotlinx.coroutines.flow.Flow
@Dao
interface LineaItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (lineaItem: LineaItem) : Long

    @Update
    fun update (lineaItem: LineaItem)
    @Delete
    fun delete(lineaItem: LineaItem)

    @Query("SELECT * FROM LineaItem l where l.id_invoice = :id")
    fun selectFromInvoice(id:Int): List<LineaItem>

    @Query("SELECT * FROM LineaItem")
    fun selectAllRAW(): List<LineaItem>

}