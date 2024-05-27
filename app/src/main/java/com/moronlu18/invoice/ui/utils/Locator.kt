package com.example.signup.utils

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.signup.data.userPreferences.InvoicePreferencesRepository

object Locator {
    public var application: Application? = null

    //inline, Cuando la llamas se inicializa y te lo da
    public inline val requiredApplication
        get()= application ?: error("Missing call: initwith(application)")

    fun initWith(application:Application){
        this.application = application
    }
    private val Context.userStore by preferencesDataStore(name = "user_preferences")

    private val Context.apiPreferences by preferencesDataStore(name ="settings")

    //by lazy se inicializas la primera vez que la llames
    val invoicePreferencesRepository by lazy {
        InvoicePreferencesRepository(requiredApplication.userStore)
    }
    /*val settingsPreferencesRepository by lazy {
        DataStorePreferencesRepository(requiredApplication.apiPreferences)
    }*/
}