package com.amadev.juniormath.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Util {

    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).apply {
            show()
        }
    }
}