package com.moronlu18.invoice.ui.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


//Esta función de extensión se podrá llamar desde cualquier Activity/o
// fragmento a través de su Activity
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

object Utils {
    //Esta función de extensión es accesible desde cualquier Activity/Fragment
    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
