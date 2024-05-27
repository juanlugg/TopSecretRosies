package com.moronlu18.customer.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert (customer: Customer) : Long

    @Update
    fun update (customer: Customer)

    @Query("SELECT * FROM customer")
    fun selectAll(): Flow<List<Customer>>

    @Query("SELECT * FROM customer")
    fun selectAllRAW(): List<Customer>
    @Delete
    fun delete(customer: Customer)
    @Query("SELECT * FROM customer c order by c.nombre")
    fun sortbyName(): Flow<List<Customer>>
    @Query("SELECT * FROM customer c where c.id = :id")
    fun selectCustomer(id: Int): Customer
}