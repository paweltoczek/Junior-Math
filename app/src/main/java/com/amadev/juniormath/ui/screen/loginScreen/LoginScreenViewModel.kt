package com.amadev.juniormath.ui.screen.loginScreen

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel(), ProvideMessage {


    private val currentUser = firebaseAuth.currentUser

    val emailInput = mutableStateOf("")
    val emailInputErrorTextState = mutableStateOf(false)
    val emailInputErrorTextValue = mutableStateOf("")

    val passwordInput = mutableStateOf("")
    val passwordInputErrorTextState = mutableStateOf(false)
    val passwordInputErrorTextValue = mutableStateOf("")

    val loginButtonState = mutableStateOf(false)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    private val _loginAutomatically = MutableLiveData<Boolean>()
    val loginAutomatically = _loginAutomatically

    fun onEmailInputChanged(input: String) {
        emailInput.value = input
        emailInputErrorTextState.value = false
    }

    fun onPasswordInputChanged(input: String) {
        passwordInput.value = input
        emailInputErrorTextState.value = false
    }

    fun validateInput() {
        when {
            emailInput.value.trim().isEmpty() -> {
                emailInputErrorTextState.value = true
                emailInputErrorTextValue.value = getMessage(fieldCantBeEmpty, context)
            }
            Patterns.EMAIL_ADDRESS.matcher(emailInput.value).matches().not() -> {
                emailInputErrorTextState.value = true
                emailInputErrorTextValue.value = getMessage(invalidEmail, context)
            }
            passwordInput.value.trim().isEmpty() -> {
                passwordInputErrorTextState.value = true
                passwordInputErrorTextValue.value = getMessage(fieldCantBeEmpty, context)
            }
            else -> {
                loginButtonState.value = true
                doLoginWithEmailAndPassword()
            }
        }
    }

    private fun doLoginWithEmailAndPassword() {
        firebaseAuth.signInWithEmailAndPassword(emailInput.value.trim(), passwordInput.value.trim())
            .addOnSuccessListener {
                if (currentUser != null) {
                    if (currentUser.isEmailVerified.not()) {
                        _popUpMessage.value = getMessage(verifyEmailSent, context)
                        loginButtonState.value = false
                    } else {
                        loginAutomatically.value = true
                    }
                }
            }
            .addOnFailureListener {
                _popUpMessage.value = getMessage(loginFailed, context)
                loginButtonState.value = false
            }
    }

    fun loginAutomaticallyIfPossible() {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                loginAutomatically.value = true
            }
        }
    }

}