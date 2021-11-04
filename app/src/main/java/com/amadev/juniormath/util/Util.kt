package com.amadev.juniormath.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


object Util {

    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).apply {
            show()
        }
    }

    fun encodeFirebaseForbiddenChars(string: String): String {
        return string
            .replace(".", "_DOT_")
            .replace("@", "_AT_")
            .replace("$", "_DOL_")
            .replace("#", "_HASH_")
            .replace("[", "_LSQRBRACKET_")
            .replace("]", "_RSQRBRACKET_")
            .replace("/", "_FORWARDSLASH_")
    }

}