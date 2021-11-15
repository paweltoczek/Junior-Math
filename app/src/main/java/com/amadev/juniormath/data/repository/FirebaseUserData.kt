package com.amadev.juniormath.data.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseUserData @Inject constructor(
    firebaseAuth: FirebaseAuth
) {

    val currentUser = firebaseAuth.currentUser
    val uuid = getFirebaseUuid()
    val userEmail = getFirebaseUserEmail()

    private fun getFirebaseUuid(): String {
        return currentUser?.uid.toString()
    }

    private fun getFirebaseUserEmail(): String {
        var email = ""
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