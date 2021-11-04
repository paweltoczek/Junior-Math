package com.amadev.juniormath.data.repository

import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.util.Categories
import com.amadev.juniormath.util.Util.encodeFirebaseForbiddenChars
import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDatabaseRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase,
    firebaseUserData: FirebaseUserData
) : RealtimeDatabaseRepository {

    companion object {
        const val REFERENCE = "users"
    }

    //User data
    private val uuid = firebaseUserData.uuid
    private val userEmail = encodeFirebaseForbiddenChars(firebaseUserData.userEmail)

    //Firebase Reference
    private val firebaseReference =
        firebaseDatabase.getReference(REFERENCE).child(uuid).child(userEmail)

    @ExperimentalCoroutinesApi
    override fun getUserAdditionScoreData() = callbackFlow<Result<UserAnswersModel?>> {
        val ref = firebaseReference.child(Categories.Addition.toString())

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
    override fun getUserSubtractionScoreData() = callbackFlow<Result<UserAnswersModel?>> {
        val ref = firebaseReference.child(Categories.Subtraction.toString())

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
    override fun getUserMultiplicationScoreData() =
        callbackFlow<Result<UserAnswersModel?>> {
            val ref = firebaseReference.child(Categories.Multiplication.toString())

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
    override fun getUserDivisionScoreData() = callbackFlow<Result<UserAnswersModel?>> {
        val ref = firebaseReference.child(Categories.Division.toString())

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
}