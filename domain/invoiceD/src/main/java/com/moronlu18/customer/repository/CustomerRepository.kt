package com.moronlu18.customer.repository

import android.database.sqlite.SQLiteException
import com.moronlu18.customer.entity.Customer
import com.moronlu18.invoice.InvoiceDatabase
import com.moronlu18.invoice.ui.utils.Resource
import kotlinx.coroutines.flow.Flow

class CustomerRepository {
    companion object {
        var clientescreados= 3;
        fun insert(c: Customer): Resource {
            return try {
                InvoiceDatabase.getInstance().customerDao().insert(c)
                clientescreados++
                Resource.Success<Customer>(c)
            } catch (e: SQLiteException) {
                Resource.Error(e)
            }
        }
        fun updateCustomer(customer: Customer): Resource {
            return try {
                InvoiceDatabase.getInstance().customerDao().update(customer)
                Resource.Success<Customer>(customer)
            } catch (e: SQLiteException) {
                println(e.message)
                Resource.Error(e)
            }
        }
        fun delete(c: Customer): Resource {
            return try {
                InvoiceDatabase.getInstance().customerDao().delete(c)
                Resource.Success<Customer>(c)
            } catch (e: SQLiteException) {
                Resource.Error(e)
            }
        }
        fun getCustomerListOrderByName(): Flow<List<Customer>> {
            return InvoiceDatabase.getInstance().customerDao().sortbyName()
        }
        fun getCustomerList(): Flow<List<Customer>> {
            return InvoiceDatabase.getInstance().customerDao().selectAll()
        }
        fun getCustomerListRAW(): List<Customer> {
            return InvoiceDatabase.getInstance().customerDao().selectAllRAW()
        }
        fun getCliente(id:Int):Customer{
            return InvoiceDatabase.getInstance().customerDao().selectCustomer(id)
        }
    }


}