package com.amadev.juniormath.ui.screen.fragments.practiceFragment

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.R
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.data.repository.FirebaseUserData
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepositoryImpl
import com.amadev.juniormath.util.ProvideMessage
import com.amadev.juniormath.util.Util.replaceFirebaseForbiddenChars
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
    private val firebaseUserData: FirebaseUserData,
    private val _realTimeDatabaseRepository: RealtimeDatabaseRepositoryImpl
) :
    ViewModel(), ProvideMessage {

    companion object {
        const val TOTAL_QUESTIONS = 15
        const val USERS = "users"
    }

    // User Details
    private val userEmail =
        replaceFirebaseForbiddenChars(firebaseUserData.userEmail)
    private val uuid = firebaseUserData.uuid

    //Categories Strings
    private val addition = context.getString(R.string.addition)
    private val subtract = context.getString(R.string.subtraction)
    private val multiplication = context.getString(R.string.multiplication)
    private val division = context.getString(R.string.division)

    //Database
    var databaseTotalQuestions = 0
    var databaseCorrectAnswers = 0

    //LiveData
    private val _userScoreData = MutableLiveData<ArrayList<UserAnswersModel>>()
    val userScoreData = _userScoreData

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
    val button1Text = mutableStateOf("0")
    val button2Text = mutableStateOf("0")
    val button3Text = mutableStateOf("0")
    val button4Text = mutableStateOf("0")
    val userCorrectAnswers = mutableStateOf(0)

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
            addition -> {

                setUpQuestionNumbersForAddition()
            }
            subtract -> {
                setUpQuestionNumbersForSubtraction()
            }
            multiplication -> {
                setUpQuestionNumbersForMultiplication()
            }
            division -> {
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
            val wrongAnswer = (fromRange.value..toRange.value).random() + (1..9).random()
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
        if (currentQuestion.value == TOTAL_QUESTIONS) {
            compareUserInputWithCorrectAnswer()
            updateButtonStates()
            clearUserAnswerInput()
            writeDataToDatabaseIfPossible()
            _finishedGame.value = true
        } else {
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

    private fun addition() = questionFirstNumbers.value + questionSecondNumbers.value
    private fun subtract() = questionFirstNumbers.value - questionSecondNumbers.value
    private fun multiplication() = questionFirstNumbers.value * questionSecondNumbers.value
    private fun division() = questionFirstNumbers.value / questionSecondNumbers.value

    // ---------------FIREBASE--------------

    @ExperimentalCoroutinesApi
    fun readFromDatabaseIfPossible() {
        if (firebaseUserData.isUserLoggedIn()) {
            getCategoryScoreData()
        }
    }

    private fun writeDataToDatabaseIfPossible() {
        if (firebaseUserData.isUserLoggedIn()) {
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

    @ExperimentalCoroutinesApi
    private fun getUserScoreDataForAddition() {
        viewModelScope.launch {
            _realTimeDatabaseRepository.getUserAdditionScoreData().collect {
                when {
                    it.isSuccess -> {
                        val data = it.getOrNull()

                        if (data.toString().isEmpty().not()){
                            val totalQuestionsData = data?.totalQuestions
                            val totalCorrectAnswersData = data?.userCorrectAnswers
                            if(!totalCorrectAnswersData.isNullOrEmpty()) {
                                databaseCorrectAnswers = totalCorrectAnswersData.toInt()
                            } else if (!totalQuestionsData.isNullOrEmpty()){
                                databaseTotalQuestions = totalQuestionsData.toInt()
                            }
                            Log.e("correct", databaseCorrectAnswers.toString())
                            Log.e("total", databaseTotalQuestions.toString())
                        }
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
                when {
                    it.isSuccess -> {
                        val subtractionData = it.getOrNull()
                        if (subtractionData != null) {
                            databaseTotalQuestions = subtractionData[0].totalQuestions.toInt()
                            databaseCorrectAnswers = subtractionData[0].totalQuestions.toInt()
                        }
                    }
                    it.isFailure -> {
                        var exception = it.exceptionOrNull()?.message
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
                when {
                    it.isSuccess -> {
                        val multiplicationData = it.getOrNull()
                        if (multiplicationData != null) {
                            databaseTotalQuestions = multiplicationData[0].totalQuestions.toInt()
                            databaseCorrectAnswers = multiplicationData[0].totalQuestions.toInt()
                        }
                    }
                    it.isFailure -> {
                        var exception = it.exceptionOrNull()?.message
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
                when {
                    it.isSuccess -> {
                        val divisionData = it.getOrNull()
                        if (divisionData != null) {
                            databaseTotalQuestions = divisionData[0].totalQuestions.toInt()
                            databaseCorrectAnswers = divisionData[0].totalQuestions.toInt()
                        }
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
    fun getCategoryScoreData() {
        when (category.value) {
            addition -> {
                getUserScoreDataForAddition()
            }
            subtract -> {
                getUserScoreDataForSubtraction()
            }
            multiplication -> {
                getUserScoreDataForMultiplication()
            }
            division -> {
                getUserScoreDataForDivision()
            }
        }
    }
}