package com.amadev.juniormath.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object Util {

    private val date = Date()
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("EEEE")


    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).apply {
            show()
        }
    }

    fun replaceFirebaseForbiddenCharsWhenSending(string: String): String {
        return string
            .replace(".", "_DOT_")
            .replace("@", "_AT_")
            .replace("$", "_DOL_")
            .replace("#", "_HASH_")
            .replace("[", "_LSQRBRACKET_")
            .replace("]", "_RSQRBRACKET_")
            .replace("/", "_FORWARDSLASH_")
    }

    fun getCurrentDayName() : String {
        return dateFormat.format(date)
    }

    fun getCurrentCurrentDate() : String {
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    }

}