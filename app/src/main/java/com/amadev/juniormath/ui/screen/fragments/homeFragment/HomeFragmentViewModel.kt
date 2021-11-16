package com.amadev.juniormath.ui.screen.fragments.homeFragment

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepository
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    @ApplicationContext private val context : Context,
    private val _realTimeDatabaseRepository: RealtimeDatabaseRepository,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel(), ProvideMessage {

    val isUserLogged = mutableStateOf(false)
    val userEmail = mutableStateOf(firebaseAuth.currentUser?.email ?: "")
    val databaseTotalQuestions = mutableStateOf(0)
    val databaseCorrectAnswers = mutableStateOf(0)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage


    fun isUserLoggedIn() {
        isUserLogged.value = firebaseAuth.currentUser != null
    }

    val dataLoaded = mutableStateOf(false)

    @ExperimentalCoroutinesApi
    fun getDataFromDatabaseIfPossible() {
        if (firebaseAuth.currentUser != null) {
            isUserEmailVerified()
            dataLoaded.value = false
            databaseCorrectAnswers.value = 0
            databaseTotalQuestions.value = 0
            getUserScoreData()
        }
    }

    @ExperimentalCoroutinesApi
    private fun getUserScoreData() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDatabaseRepository.getAllScoreData().collect {
                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()
                        data?.forEach { score ->
                            if (score != null) {
                                databaseTotalQuestions.value += score.totalQuestions.toInt()
                                databaseCorrectAnswers.value += score.userCorrectAnswers.toInt()
                            }
                        }
                        dataLoaded.value = true
                    }
                    it.isFailure -> {
                        val exception = it.exceptionOrNull()?.message
                        _popUpMessage.postValue(exception)
                    }
                }
            }
        }
    }

    private fun isUserEmailVerified() {
        if(firebaseAuth.currentUser != null) {
            if (firebaseAuth.currentUser!!.isEmailVerified.not()) {
                _popUpMessage.value = getMessage(pleaseVerifyYourEmail, context)
            }
        }
    }


}