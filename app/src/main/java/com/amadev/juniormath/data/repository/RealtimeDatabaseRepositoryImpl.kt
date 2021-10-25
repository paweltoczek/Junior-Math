package com.amadev.juniormath.data.repository

import android.content.Context
import android.util.Log
import com.amadev.juniormath.R
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.util.Util.replaceFirebaseForbiddenChars
import com.google.firebase.database.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDatabaseRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase,
    firebaseUserData: FirebaseUserData,
    @ApplicationContext context: Context
) : RealtimeDatabaseRepository {

    companion object {
        const val REFERENCE = "users"
    }

    //User data
    private val uuid = firebaseUserData.uuid
    private val userEmail = replaceFirebaseForbiddenChars(firebaseUserData.userEmail)

    //Firebase Reference
    private val firebaseReference =
        firebaseDatabase.getReference(REFERENCE).child(uuid).child(userEmail)

    private val addition = context.getString(R.string.addition)
    private val subtraction = context.getString(R.string.subtraction)
    private val multiplication = context.getString(R.string.multiplication)
    private val division = context.getString(R.string.division)

    @ExperimentalCoroutinesApi
    override fun getUserAdditionScoreData() = callbackFlow<Result<UserAnswersModel?>> {
        val ref = firebaseReference.child(addition)

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
        val ref = firebaseReference.child(subtraction)

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
            val ref = firebaseReference.child(multiplication)

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
        val ref = firebaseReference.child(division)

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