package com.amadev.juniormath.data.repository

import android.content.Context
import com.amadev.juniormath.R
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.util.Util.replaceFirebaseForbiddenChars
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDatabaseRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseUserData: FirebaseUserData,
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
    override fun getUserAdditionScoreData() = callbackFlow<Result<ArrayList<UserAnswersModel>>> {
        val ref = firebaseReference.child(addition)

        val result = ArrayList<UserAnswersModel>()
        val postListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { data ->
                    data.getValue(UserAnswersModel::class.java)?.let {
                        result.add(it)
                    }
                }
                this@callbackFlow.trySendBlocking(Result.success(result))
            }
        }
        ref.addValueEventListener(postListener)
        awaitClose {
            ref.removeEventListener(postListener)
        }
    }

    @ExperimentalCoroutinesApi
    override fun getUserSubtractionScoreData() = callbackFlow<Result<ArrayList<UserAnswersModel>>> {
        val ref = firebaseReference.child(subtraction)

        val result = ArrayList<UserAnswersModel>()
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { data ->
                    data.getValue(UserAnswersModel::class.java)?.let {
                        result.add(it)
                    }
                }
                this@callbackFlow.trySendBlocking(Result.success(result))
            }
        }
        ref.addValueEventListener(postListener)
        awaitClose {
            ref.removeEventListener(postListener)
        }
    }

    @ExperimentalCoroutinesApi
    override fun getUserMultiplicationScoreData() =
        callbackFlow<Result<ArrayList<UserAnswersModel>>> {
            val ref = firebaseReference.child(multiplication)

            val result = ArrayList<UserAnswersModel>()
            val postListener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { data ->
                        data.getValue(UserAnswersModel::class.java)?.let {
                            result.add(it)
                        }
                    }
                    this@callbackFlow.trySendBlocking(Result.success(result))
                }
            }
            ref.addValueEventListener(postListener)
            awaitClose {
                ref.removeEventListener(postListener)
            }
        }

    @ExperimentalCoroutinesApi
    override fun getUserDivisionScoreData() = callbackFlow<Result<ArrayList<UserAnswersModel>>> {
        val ref = firebaseReference.child(division)
        val result = ArrayList<UserAnswersModel>()
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { data ->
                    data.getValue(UserAnswersModel::class.java)?.let {
                        result.add(it)
                    }
                }
                this@callbackFlow.trySendBlocking(Result.success(result))
            }
        }
        ref.addValueEventListener(postListener)
        awaitClose {
            ref.removeEventListener(postListener)
        }
    }


}