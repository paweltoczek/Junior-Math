package com.amadev.juniormath.data.repository

import android.util.Log
import com.amadev.juniormath.data.model.CategoryModel
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.util.Util
import com.amadev.juniormath.util.Util.replaceFirebaseForbiddenCharsWhenSending
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDatabaseRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : RealtimeDatabaseRepository {

    companion object {
        const val REFERENCE = "users"
    }

    private val currentUser = firebaseAuth.currentUser
    private val uuid = getFirebaseUuid()

    private val userEmail = getFirebaseUserEmail()

    val addition = "Addition"
    val subtraction = "Subtraction"
    val multiplication = "Multplication"
    val division = "Division"
    val currentDate = Util.replaceFirebaseForbiddenCharsWhenSending(Util.getCurrentCurrentDate())
    val currentDay = Util.getCurrentDayName()


    private fun getFirebaseUuid(): String {
        return currentUser?.uid.toString()
    }

    private fun getFirebaseUserEmail(): String {
        val email = currentUser?.email
        var replaced = ""
        if (email != null) {
            replaced = replaceFirebaseForbiddenCharsWhenSending(email)
        }
        return replaced
    }

    private fun isUserLoggedIn(): Boolean {
        var isLoggedIn = false
        if (currentUser != null) {
            isLoggedIn = true
        }
        return isLoggedIn
    }

    @ExperimentalCoroutinesApi
    override fun getUserAdditionScoreData() = callbackFlow<Result<ArrayList<UserAnswersModel>>> {
        val ref = firebaseDatabase.getReference(REFERENCE).child(uuid).child(userEmail).child(addition)
        val query: Query = ref
        val result = ArrayList<UserAnswersModel>()
        val postListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{ data ->
                    data.getValue(UserAnswersModel::class.java)?.let {
                        result.add(it)
                    }
                }
                this@callbackFlow.trySendBlocking(Result.success(result))
            }
        }

        ref.addValueEventListener(postListener)

        awaitClose {
            query.removeEventListener(postListener)
        }
    }


}