package com.amadev.juniormath.ui.screen.fragments.practiceFragment

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.R
import com.amadev.juniormath.util.ProvideMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PracticeFragmentViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel(), ProvideMessage {

    private val addition = context.getString(R.string.addition)
    private val subtract = context.getString(R.string.subtraction)
    private val multiplication = context.getString(R.string.multiplication)
    private val division = context.getString(R.string.division)

    private val category = mutableStateOf("")
    private val fromRange = mutableStateOf(0)
    private val toRange = mutableStateOf(0)
    private val correctAnswer = mutableStateOf(0)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    val operator = mutableStateOf("")
    val userAnswerInput = mutableStateOf("")
    val currentQuestion = mutableStateOf(1)
    val questionNumberFirst = mutableStateOf(0)
    val questionNumberSecond = mutableStateOf(0)
    val button1State = mutableStateOf(false)
    val button2State = mutableStateOf(false)
    val button3State = mutableStateOf(false)
    val button4State = mutableStateOf(false)
    val button1Text = mutableStateOf("0")
    val button2Text = mutableStateOf("0")
    val button3Text = mutableStateOf("0")
    val button4Text = mutableStateOf("0")

    val userCorrectAnswers = mutableStateOf(0)
    val totalQuestions = mutableStateOf(0)

    private fun addition() = questionNumberFirst.value + questionNumberSecond.value
    private fun subtract() = questionNumberFirst.value - questionNumberSecond.value
    private fun multiplication() = questionNumberFirst.value * questionNumberSecond.value
    private fun division() = questionNumberFirst.value / questionNumberSecond.value

    fun onButtonStateChanged(button: String) {
        when (button) {
            button1State.toString() -> {
                button1State.value = !button1State.value
                if (button1State.value) {
                    button2State.value = false
                    button3State.value = false
                    button4State.value = false
                }
            }

            button2State.toString() -> {
                button2State.value = !button2State.value
                if (button2State.value) {
                    button1State.value = false
                    button3State.value = false
                    button4State.value = false
                }
            }

            button3State.toString() -> {
                button3State.value = !button3State.value
                if (button3State.value) {
                    button1State.value = false
                    button2State.value = false
                    button4State.value = false
                }
            }

            button4State.toString() -> {
                button4State.value = !button4State.value
                if (button4State.value) {
                    button1State.value = false
                    button2State.value = false
                    button3State.value = false
                }
            }
        }
    }


    private fun compareUserInputWithCorrectAnswer() {
        if (userAnswerInput.value.toInt() == correctAnswer.value) userCorrectAnswers.value ++
        Log.e("userans", userCorrectAnswers.value.toString())
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
                setUpAnswers(correctAnswer.value)
            }
            subtract -> {
                correctAnswer.value = subtract()
                operator.value = "-"
                setUpAnswers(correctAnswer.value)

            }
            multiplication -> {
                correctAnswer.value = multiplication()
                operator.value = "*"
                setUpAnswers(correctAnswer.value)

            }
            division -> {
                correctAnswer.value = division()
                operator.value = "/"
                setUpAnswers(correctAnswer.value)

            }
        }
    }

    private fun setUpAnswers(correctAnswer: Int) {
        val answersArrayList = ArrayList<Int>()

        answersArrayList.clear()
        answersArrayList.add(correctAnswer)

        while (true) {
            val wrongAnswer = (fromRange.value..toRange.value).random() + this.correctAnswer.value
            if (answersArrayList.contains(wrongAnswer).not()) {
                answersArrayList.add(wrongAnswer)
                if (answersArrayList.size == 4) break
            }
        }

        val shuffledList = answersArrayList.shuffled()

        button1Text.value = shuffledList[0].toString()
        button2Text.value = shuffledList[1].toString()
        button3Text.value = shuffledList[2].toString()
        button4Text.value = shuffledList[3].toString()

    }

    private fun updateButtonStates() {
        false.also {
            button1State.value = it
            button2State.value = it
            button3State.value = it
            button4State.value = it
        }
    }

    fun validateUserInput() {
        if (userAnswerInput.value == "") {
            _popUpMessage.value = getMessage(selectYourAnswer, context)
        } else {
            compareUserInputWithCorrectAnswer()
            updateButtonStates()
            clearUserAnswerInput()
            getNextQuestion()
        }
    }

}