package com.amadev.juniormath.ui.screen.dialogs.verifyEmailDialog

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class VerifyEmailDialogViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel(), ProvideMessage {

    private val _emailVerified = MutableLiveData<Boolean>()
    val emailVerified = _emailVerified

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    fun isEmailVerified() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                _emailVerified.value = true
            } else {
                _emailVerified.value = false
                _popUpMessage.value = getMessage(pleaseVerifyEmailSent, context)
            }
        }
    }
}