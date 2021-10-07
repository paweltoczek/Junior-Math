package com.amadev.juniormath.ui.screen.fragments.practiceFragment

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PracticeFragmentViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    private val addition = context.getString(R.string.addition)
    private val subtract = context.getString(R.string.subtraction)
    private val multiplication = context.getString(R.string.multiplication)
    private val division = context.getString(R.string.division)

    val operator = mutableStateOf("")
    val userAnswerInput = mutableStateOf("")
    val currentQuestion = mutableStateOf(1)
    val questionNumberFirst = mutableStateOf(0)
    val questionNumberSecond = mutableStateOf(0)

    private val category = mutableStateOf("")
    private val fromRange = mutableStateOf(0)
    private val toRange = mutableStateOf(0)
    private val correctAnswer = mutableStateOf(0)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage


    fun compareUserInputWithCorrectAnswer() {
        if (userAnswerInput.value.toInt() == correctAnswer.value) {
            popUpMessage.value = "Correct"
        } else {
            popUpMessage.value = "Incorrect"
        }
        getNextQuestion()
    }

    fun setUpCategory(category: String) {
        this.category.value = category
        setUpCorrectAnswer()
    }

    fun handleRanges(rangeFrom: Int?, rangeTo: Int?) {
        if (rangeFrom != null && rangeTo != null) {
            fromRange.value = rangeFrom
            toRange.value = rangeTo
        }
        setUpQuestionNumbers()
    }

    fun onAnswerInputChanged(input: String) {
        userAnswerInput.value = input
    }

    private fun getNextQuestion() {
        currentQuestion.value = currentQuestion.value + 1
        setUpQuestionNumbers()
        clearUserAnswerInput()
    }

    private fun clearUserAnswerInput() {
        userAnswerInput.value = ""
    }

    fun setUpQuestionNumbers() {
        questionNumberFirst.value = (fromRange.value..toRange.value).random()
        questionNumberSecond.value = (fromRange.value..toRange.value).random()
        setUpCorrectAnswer()
    }

    private fun setUpCorrectAnswer() {
        when (category.value) {
            addition -> {
                correctAnswer.value = addition()
                operator.value = "+"
                Log.e("cor", correctAnswer.value.toString())
            }
            subtract -> {
                correctAnswer.value = subtract()
                operator.value = "-"
            }
            multiplication -> {
                correctAnswer.value = multiplication()
                operator.value = "*"
            }
            division -> {
                correctAnswer.value = division()
                operator.value = "/"
            }
        }
    }


    private fun addition() = questionNumberFirst.value + questionNumberSecond.value
    private fun subtract() = questionNumberFirst.value - questionNumberSecond.value
    private fun multiplication() = questionNumberFirst.value * questionNumberSecond.value
    private fun division() = questionNumberFirst.value / questionNumberSecond.value
}