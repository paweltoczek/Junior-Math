package com.amadev.juniormath.ui.screen.fragments.practiceFragment

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepository
import com.amadev.juniormath.util.Categories
import com.amadev.juniormath.util.ProvideMessage
import com.amadev.juniormath.util.Util.encodeFirebaseForbiddenChars
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeFragmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val _realTimeDatabaseRepository: RealtimeDatabaseRepository
) :
    ViewModel(), ProvideMessage {

    companion object {
        const val TOTAL_QUESTIONS = 15
        const val USERS = "users"
        const val ADDITION_OPERATOR = "+"
        const val SUBTRACTION_OPERATOR = "-"
        const val MULTIPLICATION_OPERATOR = "*"
        const val DIVISION_OPERATOR = "/"
    }

    // User Details
    private val userEmail =
        encodeFirebaseForbiddenChars(firebaseAuth.currentUser?.email ?: "")
    private val uuid = firebaseAuth.currentUser?.uid ?: ""

    //Database
    var databaseTotalQuestions = 0
    var databaseCorrectAnswers = 0

    //LiveData
    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage
    private val _finishedGame = MutableLiveData<Boolean>()
    val finishedGame = _finishedGame

    //Private mutableStateOf
    private val category = mutableStateOf("")
    private val fromRange = mutableStateOf(0)
    private val toRange = mutableStateOf(0)
    private val correctAnswer = mutableStateOf(0)

    //mutableStateOf
    val operator = mutableStateOf("")
    val userAnswerInput = mutableStateOf("")
    val currentQuestion = mutableStateOf(1)
    val questionFirstNumbers = mutableStateOf(0)
    val questionSecondNumbers = mutableStateOf(0)
    val button1State = mutableStateOf(false)
    val button2State = mutableStateOf(false)
    val button3State = mutableStateOf(false)
    val button4State = mutableStateOf(false)
    val answerButton1Text = mutableStateOf("0")
    val answerButton2Text = mutableStateOf("0")
    val answerButton3Text = mutableStateOf("0")
    val answerButton4Text = mutableStateOf("0")
    val userCorrectAnswers = mutableStateOf(0)

    fun onButtonStateChanged(button: String) {
        when (button) {
            button1State.toString() -> {
                button1State.value = !button1State.value
                if (button1State.value) {
                    false.also {
                        button2State.value = it
                        button3State.value = it
                        button4State.value = it
                    }
                }
            }

            button2State.toString() -> {
                button2State.value = !button2State.value
                if (button2State.value) {
                    false.also {
                        button1State.value = it
                        button3State.value = it
                        button4State.value = it
                    }
                }
            }

            button3State.toString() -> {
                button3State.value = !button3State.value
                if (button3State.value) {
                    false.also {
                        button1State.value = it
                        button2State.value = it
                        button4State.value = it
                    }
                }
            }

            button4State.toString() -> {
                button4State.value = !button4State.value
                if (button4State.value) {
                    false.also {
                        button1State.value = it
                        button2State.value = it
                        button3State.value = it
                    }
                }
            }
        }
    }


    private fun compareUserInputWithCorrectAnswer() {
        if (userAnswerInput.value.toInt() == correctAnswer.value) userCorrectAnswers.value ++
    }

    fun setUpCategory(category: String) {
        this.category.value = category
    }

    fun handleRanges(rangeFrom: Int?, rangeTo: Int?) {
        if (rangeFrom != null && rangeTo != null) {
            fromRange.value = rangeFrom
            toRange.value = rangeTo
        }
        handleCategoriesNumbers()
    }

    private fun getNextQuestion() {
        currentQuestion.value = currentQuestion.value + 1
        handleCategoriesNumbers()
        clearUserAnswerInput()
    }

    private fun clearUserAnswerInput() {
        userAnswerInput.value = ""
    }

    fun handleCategoriesNumbers() {
        when (category.value) {
            Categories.Addition.name -> {
                setUpQuestionNumbersForAddition()
            }
            Categories.Subtraction.name -> {
                setUpQuestionNumbersForSubtraction()
            }
            Categories.Multiplication.name -> {
                setUpQuestionNumbersForMultiplication()
            }
            Categories.Division.name -> {
                setUpQuestionNumbersForDivision()
            }
        }
    }

    private fun setUpQuestionNumbersForDivision() {
        while (true) {
            val firstNumber = (fromRange.value..toRange.value).random()
            val secondNumber = (fromRange.value..toRange.value).random()

            if (firstNumber / secondNumber != 0) {
                if (firstNumber.toDouble() % secondNumber.toDouble() == 0.00) {
                    questionFirstNumbers.value = firstNumber
                    questionSecondNumbers.value = secondNumber
                    setUpCorrectAnswer()
                    break
                }
            }
        }
    }

    private fun setUpQuestionNumbersForMultiplication() {
        questionFirstNumbers.value = (fromRange.value..toRange.value).random()
        questionSecondNumbers.value = (fromRange.value..toRange.value).random()
        setUpCorrectAnswer()
    }

    private fun setUpQuestionNumbersForAddition() {
        questionFirstNumbers.value = (fromRange.value..toRange.value).random()
        questionSecondNumbers.value = (fromRange.value..toRange.value).random()
        setUpCorrectAnswer()
    }

    private fun setUpQuestionNumbersForSubtraction() {
        while (true) {
            val firstNumber = (fromRange.value..toRange.value).random()
            val secondNumber = (fromRange.value..toRange.value).random()
            if (firstNumber > secondNumber) {
                questionFirstNumbers.value = firstNumber
                questionSecondNumbers.value = secondNumber
                setUpCorrectAnswer()
                break
            }
        }
    }

    private fun setUpCorrectAnswer() {
        when (category.value) {
            Categories.Addition.name -> {
                correctAnswer.value = addition()
                operator.value = ADDITION_OPERATOR
                setUpButtonAnswers(correctAnswer.value)
            }
            Categories.Subtraction.name -> {
                correctAnswer.value = subtract()
                operator.value = SUBTRACTION_OPERATOR
                setUpButtonAnswers(correctAnswer.value)

            }
            Categories.Multiplication.name -> {
                correctAnswer.value = multiplication()
                operator.value = MULTIPLICATION_OPERATOR
                setUpButtonAnswers(correctAnswer.value)
            }
            Categories.Division.name -> {
                correctAnswer.value = division()
                operator.value = DIVISION_OPERATOR
                setUpButtonAnswers(correctAnswer.value)
            }
        }
    }

    private fun setUpButtonAnswers(correctAnswer: Int) {
        val answersArrayList = ArrayList<Int>()

        answersArrayList.clear()
        answersArrayList.add(correctAnswer)

        while (true) {
            val wrongAnswer = correctAnswer + (-2..2).random()
            if (answersArrayList.contains(wrongAnswer).not()) {
                answersArrayList.add(wrongAnswer)
                if (answersArrayList.size == 4) break
            }
        }

        val shuffledList = answersArrayList.shuffled()

        answerButton1Text.value = shuffledList[0].toString()
        answerButton2Text.value = shuffledList[1].toString()
        answerButton3Text.value = shuffledList[2].toString()
        answerButton4Text.value = shuffledList[3].toString()

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
        if (isAnswerSelected()) {
            if (currentQuestion.value == TOTAL_QUESTIONS) {
                compareUserInputWithCorrectAnswer()
                updateButtonStates()
                clearUserAnswerInput()
                getNextQuestion()
                writeDataToDatabaseIfPossible()
                _finishedGame.value = true
            } else {
                compareUserInputWithCorrectAnswer()
                updateButtonStates()
                clearUserAnswerInput()
                getNextQuestion()
            }
        } else {
            _popUpMessage.value = getMessage(selectYourAnswer, context)
        }
    }

    private fun addition() = questionFirstNumbers.value + questionSecondNumbers.value
    private fun subtract() = questionFirstNumbers.value - questionSecondNumbers.value
    private fun multiplication() = questionFirstNumbers.value * questionSecondNumbers.value
    private fun division() = questionFirstNumbers.value / questionSecondNumbers.value

    // ---------------FIREBASE--------------

    @ExperimentalCoroutinesApi
    fun readFromDatabaseIfPossible() {
        if (firebaseAuth.currentUser != null) {
            getDatabaseCategoryScoreData()
        }
    }

    @ExperimentalCoroutinesApi
    fun getDatabaseCategoryScoreData() {
        when (category.value) {
            Categories.Addition.name -> {
                getUserScoreDataForAddition()
            }
            Categories.Subtraction.name -> {
                getUserScoreDataForSubtraction()
            }
            Categories.Multiplication.name -> {
                getUserScoreDataForMultiplication()
            }
            Categories.Division.name -> {
                getUserScoreDataForDivision()
            }
        }
    }

    private fun writeDataToDatabaseIfPossible() {
        if (firebaseAuth.currentUser != null) {
            val ref = firebaseDatabase.getReference(USERS)
                .child(uuid)
                .child(userEmail)
                .child(category.value)

            val totalQuestions = (databaseTotalQuestions + TOTAL_QUESTIONS).toString()
            val correctAnswers = (databaseCorrectAnswers + userCorrectAnswers.value).toString()

            ref.setValue(
                UserAnswersModel(
                    userCorrectAnswers = correctAnswers,
                    totalQuestions = totalQuestions
                )
            )
                .addOnCompleteListener {
                    _popUpMessage.value = it.result.toString()
                }
                .addOnSuccessListener {
                    _popUpMessage.value = getMessage(dataSaved, context)
                }
                .addOnFailureListener {
                    _popUpMessage.value = it.message
                }
        }
    }

    private fun isAnswerSelected(): Boolean {
        return !(!button1State.value
                && !button4State.value
                && !button3State.value
                && !button4State.value)
    }

    @ExperimentalCoroutinesApi
    private fun getUserScoreDataForAddition() {
        viewModelScope.launch {
            _realTimeDatabaseRepository.getUserCategoryScoreData(Categories.Addition.name).collect {
                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()
                        databaseTotalQuestions = data?.totalQuestions?.toInt() ?: 0
                        databaseCorrectAnswers = data?.userCorrectAnswers?.toInt() ?: 0
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
            _realTimeDatabaseRepository.getUserCategoryScoreData(Categories.Subtraction.name)
                .collect {
                    when {
                        it.isSuccess -> {
                            val data = it.getOrNull()
                            databaseTotalQuestions = data?.totalQuestions?.toInt() ?: 0
                            databaseCorrectAnswers = data?.userCorrectAnswers?.toInt() ?: 0
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
            _realTimeDatabaseRepository.getUserCategoryScoreData(Categories.Multiplication.name)
                .collect {
                    when {
                        it.isSuccess -> {
                            val data = it.getOrNull()
                            databaseTotalQuestions = data?.totalQuestions?.toInt() ?: 0
                            databaseCorrectAnswers = data?.userCorrectAnswers?.toInt() ?: 0
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
            _realTimeDatabaseRepository.getUserCategoryScoreData(Categories.Division.name).collect {
                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()
                        databaseTotalQuestions = data?.totalQuestions?.toInt() ?: 0
                        databaseCorrectAnswers = data?.userCorrectAnswers?.toInt() ?: 0
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