package com.moronlu18.InvoiceDavid.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moronlu18.invoice.entity.Invoice
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert (invoice: Invoice) : Long


    @Update
    fun update (invoice: Invoice)
    //Se añade una query personalizada en un método que devuelve un objeto Account y el objeto
    //BussinessProfile
    @Delete
    fun delete(invoice: Invoice)

    @Query("SELECT * FROM invoice")
    fun selectAll(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoice i  order by i.idCliente")
    fun selectAllOrderByIdCliente(): Flow<List<Invoice>>
    @Query("SELECT * FROM invoice")
    fun selectAllRAW(): List<Invoice>



    //@Delete
    //fun delete(invoice: Invoice)

/*
    @Query("SELECT * FROM invoice JOIN bussinesprofile ON account.bussinesprofile=bussinesProfile.id")
    fun loadAccountAndBusinessProfile() : Map<Account, BussinessProfile>*/
    //como incorporar relaciones
}