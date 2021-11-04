package com.amadev.juniormath.ui.screen.fragments.welcomeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.data.repository.FirebaseUserData
import com.amadev.juniormath.util.ProvideMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class WelcomeFragmentViewModel @Inject constructor(
    private val firebaseUserData: FirebaseUserData
) :
    ViewModel(), ProvideMessage {

    private val _loginAutomaticallyIfPossible = MutableLiveData<Boolean>()
    val loginAutomaticallyIfPossible = _loginAutomaticallyIfPossible

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage


    fun loginAutomaticallyIfPossible() {
        Timer("delay", false).schedule(2000) {
            _loginAutomaticallyIfPossible.postValue(firebaseUserData.isUserLoggedIn())
        }
    }
}