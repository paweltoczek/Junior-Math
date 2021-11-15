package com.amadev.juniormath.ui.screen.fragments.homeFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.repository.FirebaseUserData
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    firebaseUserData: FirebaseUserData,
    private val _realTimeDatabaseRepository: RealtimeDatabaseRepository
) : ViewModel() {

    private val currentUser = firebaseUserData.currentUser
    val isUserLogged = firebaseUserData.isUserLoggedIn()
    val userEmail = firebaseUserData.userEmail

    val databaseTotalQuestions = mutableStateOf(0)
    val databaseCorrectAnswers = mutableStateOf(0)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    val dataLoaded = mutableStateOf(false)

    @ExperimentalCoroutinesApi
    fun getDataFromDatabaseIfPossible() {
        if (currentUser != null) {
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

}