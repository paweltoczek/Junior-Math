package com.amadev.juniormath.data.firebase

import androidx.lifecycle.MutableLiveData
import com.amadev.juniormath.util.Util.getCurrentCurrentDate
import com.amadev.juniormath.util.Util.getCurrentDayName
import com.amadev.juniormath.util.Util.replaceFirebaseForbiddenChars
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    val firebaseDatabase: FirebaseDatabase
) {

    //Do not change these values !!
    companion object {
        const val USERS = "users"
    }

    private val currentUser = firebaseAuth.currentUser
    val uuid = getFirebaseUuid()

    val userEmail = getFirebaseUserEmail()?.let {
        replaceFirebaseForbiddenChars(it)
    }

    val addition = "Addition"
    val subtraction = "Subtraction"
    val multiplication = "Multplication"
    val division = "Division"
    val currentDate = replaceFirebaseForbiddenChars(getCurrentCurrentDate())
    val currentDay = getCurrentDayName()

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    private fun getFirebaseUuid(): String {
        return currentUser?.uid.toString()
    }

    private fun getFirebaseUserEmail(): String? {
        return currentUser?.email
    }

    fun isUserLoggedIn(): Boolean {
        var isLoggedIn = false
        if (currentUser != null) {
            isLoggedIn = true
        }
        return isLoggedIn
    }


    fun getAdditionData(category: String): Query? {
        var query: Query? = null
        if (isUserLoggedIn()) {
            if (userEmail != null) {
                val ref = firebaseDatabase.getReference("users")
                    .child(uuid)
                    .child(userEmail.toString())
                    .child(category)
                query = ref
            }
        }
        return query
    }


}