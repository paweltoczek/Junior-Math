package com.amadev.juniormath.data.repository

import com.amadev.juniormath.util.Util.replaceFirebaseForbiddenChars
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class FirebaseUserData @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    val firebaseDatabase: FirebaseDatabase
) {

    private val currentUser = firebaseAuth.currentUser
    val uuid = getFirebaseUuid()
    val userEmail = getFirebaseUserEmail()?.let {
        replaceFirebaseForbiddenChars(it)
    }

    private fun getFirebaseUuid(): String {
        return currentUser?.uid.toString()
    }

    private fun getFirebaseUserEmail(): String {
        var email = ""
        val user = firebaseAuth.currentUser
        if (currentUser != null) {
            email = currentUser.email.toString()
        }
        return email
    }

    fun isUserLoggedIn(): Boolean {
        var isLoggedIn = false
        if (currentUser != null) {
            isLoggedIn = true
        }
        return isLoggedIn
    }
}