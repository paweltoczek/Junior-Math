package com.amadev.juniormath.ui.screen.fragments.homeFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.repository.FirebaseUserData
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val firebaseUserData: FirebaseUserData,
    private val _realTimeDatabaseRepository: RealtimeDatabaseRepositoryImpl
) : ViewModel() {

    private val currentUser = firebaseUserData.currentUser
    val userEmail = firebaseUserData.userEmail

    var databaseTotalQuestions = mutableStateOf(0)
    var databaseCorrectAnswers = mutableStateOf(0)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    val allFunctionsCalled = mutableStateOf(0)

    @ExperimentalCoroutinesApi
    fun getDataFromDatabaseIfPossible() {
        if (currentUser != null) {
            databaseCorrectAnswers.value = 0
            databaseTotalQuestions.value = 0
            allFunctionsCalled.value = 0

            getUserScoreDataForAddition()
            getUserScoreDataForDivision()
            getUserScoreDataForMultiplication()
            getUserScoreDataForSubtraction()
        }
    }

    @ExperimentalCoroutinesApi
    private fun getUserScoreDataForAddition() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDatabaseRepository.getUserAdditionScoreData().collect {
                allFunctionsCalled.value += 1
                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()
                        val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                        val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0
                        databaseTotalQuestions.value = databaseTotalQuestions.value + totalQuestions
                        databaseCorrectAnswers.value = databaseCorrectAnswers.value + correctAnswers
                    }
                    it.isFailure -> {
                        val exception = it.exceptionOrNull()?.message
                        _popUpMessage.postValue(exception)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun getUserScoreDataForSubtraction() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDatabaseRepository.getUserSubtractionScoreData().collect {
                allFunctionsCalled.value += 1

                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()

                        val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                        val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0

                        databaseTotalQuestions.value = databaseTotalQuestions.value + totalQuestions
                        databaseCorrectAnswers.value = databaseCorrectAnswers.value + correctAnswers
                    }

                    it.isFailure -> {
                        val exception = it.exceptionOrNull()?.message
                        _popUpMessage.postValue(exception)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun getUserScoreDataForMultiplication() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDatabaseRepository.getUserMultiplicationScoreData().collect {
                allFunctionsCalled.value += 1

                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()

                        val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                        val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0

                        databaseTotalQuestions.value = databaseTotalQuestions.value + totalQuestions
                        databaseCorrectAnswers.value = databaseCorrectAnswers.value + correctAnswers
                    }

                    it.isFailure -> {
                        val exception = it.exceptionOrNull()?.message
                        _popUpMessage.postValue(exception)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun getUserScoreDataForDivision() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDatabaseRepository.getUserDivisionScoreData().collect {
                allFunctionsCalled.value += 1

                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()

                        val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                        val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0

                        databaseTotalQuestions.value = databaseTotalQuestions.value + totalQuestions
                        databaseCorrectAnswers.value = databaseCorrectAnswers.value + correctAnswers
                    }

                    it.isFailure -> {
                        val exception = it.exceptionOrNull()?.message
                        _popUpMessage.postValue(exception)
                    }
                }
            }
        }
    }

}