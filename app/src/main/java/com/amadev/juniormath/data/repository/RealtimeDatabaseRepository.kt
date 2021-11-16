package com.amadev.juniormath.data.repository

import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.util.Util.encodeFirebaseForbiddenChars
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDatabaseRepository @Inject constructor(
    firebaseDatabase: FirebaseDatabase,
    firebaseAuth: FirebaseAuth,
) {

    companion object {
        const val REFERENCE = "users"
    }

    //User data
    private val uuid = firebaseAuth.currentUser?.uid ?: ""
    private val userEmail = encodeFirebaseForbiddenChars(firebaseAuth.currentUser?.email ?: "")

    //Firebase Reference
    private val firebaseReference =
        firebaseDatabase.getReference(REFERENCE).child(uuid).child(userEmail)

    @ExperimentalCoroutinesApi
    fun getUserCategoryScoreData(category : String) = callbackFlow<Result<UserAnswersModel?>> {
        val ref = firebaseReference.child(category)

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(UserAnswersModel::class.java)
                this@callbackFlow.trySendBlocking(Result.success(data))
            }
        }
        ref.addValueEventListener(postListener)
        awaitClose {
            ref.removeEventListener(postListener)
        }
    }

    @ExperimentalCoroutinesApi
    fun getAllScoreData() = callbackFlow<Result<ArrayList<UserAnswersModel?>>> {
        val ref = firebaseReference
        val data = ArrayList<UserAnswersModel?>()
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    data.add(it.getValue(UserAnswersModel::class.java))
                }
                this@callbackFlow.trySendBlocking(Result.success(data))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }
        }

        ref.addValueEventListener(postListener)

        awaitClose {
            ref.removeEventListener(postListener)
        }
    }
}