package com.example.signup.data.userPreferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class InvoicePreferencesRepository(private val dataStore: DataStore<Preferences>) {
    fun saveInvoiceOr(orID: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[INVOICE_OR] = orID ?: "none"
            }
        }

    }
    fun saveTheme(theme: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[THEME] = theme ?: "none"
            }
        }

    }
    fun getTheme(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[THEME] ?: "none"
            }.first()
        }
    }

    fun saveLanguaje(theme: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[LANGUAJE] = theme
            }
        }
    }
    fun getLanguaje(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[LANGUAJE] ?: "none"
            }.first()
        }
    }

    fun getInvoiceOr(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[INVOICE_OR] ?: "none"
            }.first()
        }
    }
    fun saveItemOrder(order: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[ITEM_ORDER] = order
            }
        }
    }
    fun getItemOrder(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[ITEM_ORDER] ?: "default_value"
            }.first()
        }
    }
    fun saveTaskOrder(order: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[TASK_ORDER] = order
            }
        }
    }

    fun getTaskOrder(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[TASK_ORDER] ?: "default"
            }.first()
        }
    }
    fun saveCustomerOr(orID: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[CUSTOMER] = orID ?: "none"
            }
        }

    }
    fun getCustomerOr(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[CUSTOMER] ?: "none"
            }.first()
        }
    }

    companion object {
        private val THEME = stringPreferencesKey("theme")
        private val LANGUAJE = stringPreferencesKey("languaje")
        private val CUSTOMER = stringPreferencesKey("customer_order")
        private val INVOICE_OR = stringPreferencesKey("invoice_or")
        private val ITEM_ORDER = stringPreferencesKey("item_order")
        private val TASK_ORDER = stringPreferencesKey("task_order")
    }
}