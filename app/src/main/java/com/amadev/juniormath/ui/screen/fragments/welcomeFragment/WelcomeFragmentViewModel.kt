package com.amadev.juniormath.ui.screen.fragments.welcomeFragment

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WelcomeFragmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel(), ProvideMessage {

    private val currentUser = firebaseAuth.currentUser

    private val _loginAutomaticallyIfPossible = MutableLiveData<Boolean>()
    val loginAutomaticallyIfPossible = _loginAutomaticallyIfPossible

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    fun loginAutomaticallyIfPossible() {
        if (currentUser != null) {
            if (currentUser.isEmailVerified.not()) {
                _loginAutomaticallyIfPossible.value = false
            } else {
                Handler(Looper.myLooper()!!).postDelayed({
                    _loginAutomaticallyIfPossible.value = true
                }, 2000)
            }
        }
    }
}