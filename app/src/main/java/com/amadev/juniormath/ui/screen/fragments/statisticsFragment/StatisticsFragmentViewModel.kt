package com.amadev.juniormath.ui.screen.fragments.statisticsFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepository
import com.amadev.juniormath.util.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsFragmentViewModel @Inject constructor(
    private val _realTimeDataBaseRepository: RealtimeDatabaseRepository
) : ViewModel() {

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    var additionScoreData =
        mutableStateOf(UserAnswersModel(userCorrectAnswers = "0", totalQuestions = "0"))
    var subtractionScoreData =
        mutableStateOf(UserAnswersModel(userCorrectAnswers = "0", totalQuestions = "0"))
    var multiplicationScoreData =
        mutableStateOf(UserAnswersModel(userCorrectAnswers = "0", totalQuestions = "0"))
    var divisionScoreData =
        mutableStateOf(UserAnswersModel(userCorrectAnswers = "0", totalQuestions = "0"))

    val additionDataLoaded = mutableStateOf(false)
    val subtractionDataLoaded = mutableStateOf(false)
    val multiplicationDataLoaded = mutableStateOf(false)
    val divisionDataLoaded = mutableStateOf(false)

    @ExperimentalCoroutinesApi
    fun getUserScoreData() {
        false.also {
            additionDataLoaded.value = it
            subtractionDataLoaded.value = it
            multiplicationDataLoaded.value = it
            divisionDataLoaded.value = it
        }
        getUserAdditionData()
        getUserSubtractionData()
        getUserMultiplicationData()
        getUserDivisionScoreData()
    }

    @ExperimentalCoroutinesApi
    private fun getUserAdditionData() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDataBaseRepository.getUserCategoryScoreData(Categories.Addition.name).collect {
                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()
                        val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                        val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0

                        additionScoreData.value = UserAnswersModel(
                            userCorrectAnswers = "$correctAnswers",
                            totalQuestions = "$totalQuestions"
                        )
                        additionDataLoaded.value = true
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
    private fun getUserSubtractionData() {
        viewModelScope.launch {
            _realTimeDataBaseRepository.getUserCategoryScoreData(Categories.Subtraction.name)
                .collect {
                    when {
                        it.isSuccess -> {
                            val data = it.getOrNull()
                            val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                            val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0

                            subtractionScoreData.value = UserAnswersModel(
                                userCorrectAnswers = "$correctAnswers",
                                totalQuestions = "$totalQuestions"
                            )
                            subtractionDataLoaded.value = true
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
    private fun getUserMultiplicationData() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDataBaseRepository.getUserCategoryScoreData(Categories.Multiplication.name)
                .collect {
                    when {
                        it.isSuccess -> {
                            val data = it.getOrNull()
                            val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                            val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0

                            multiplicationScoreData.value = UserAnswersModel(
                                userCorrectAnswers = "$correctAnswers",
                                totalQuestions = "$totalQuestions"
                            )
                            multiplicationDataLoaded.value = true
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
    private fun getUserDivisionScoreData() {
        viewModelScope.launch(Dispatchers.IO) {
            _realTimeDataBaseRepository.getUserCategoryScoreData(Categories.Division.name).collect {
                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()
                        val totalQuestions = data?.totalQuestions?.toInt() ?: 0
                        val correctAnswers = data?.userCorrectAnswers?.toInt() ?: 0

                        divisionScoreData.value = UserAnswersModel(
                            userCorrectAnswers = "$correctAnswers",
                            totalQuestions = "$totalQuestions"
                        )
                        divisionDataLoaded.value = true
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