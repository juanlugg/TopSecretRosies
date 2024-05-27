package com.example.signup.utils

import android.app.Application

class InvoiceAplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Locator.initWith(this)
    }
}