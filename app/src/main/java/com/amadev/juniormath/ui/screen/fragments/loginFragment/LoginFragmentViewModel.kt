package com.amadev.juniormath.ui.screen.fragments.loginFragment

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.repository.FirebaseUserData
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    firebaseUserData: FirebaseUserData
) : ViewModel(), ProvideMessage {

    private val currentUser = firebaseUserData.currentUser

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
        passwordInputErrorTextState.value = false
    }

    fun validateInput() {
        when {
            emailInput.value.trim().isEmpty() -> {
                emailInputErrorTextState.value = true
                emailInputErrorTextValue.value = getMessage(fieldCantBeEmpty, context)
            }
            Patterns.EMAIL_ADDRESS.matcher(emailInput.value.trim()).matches().not() -> {
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
        viewModelScope.launch(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(
                emailInput.value.trim(),
                passwordInput.value.trim()
            )
                .addOnSuccessListener {
                    if (currentUser != null) {
                        if (currentUser.isEmailVerified.not()) {
                            _loginAutomatically.postValue(false)
                            _popUpMessage.postValue(getMessage(verifyEmailSent, context))
                        } else {
                            _loginAutomatically.postValue(true)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    loginButtonState.value = false
                    _popUpMessage.postValue(exception.message)
                }
        }
    }

    fun loginAutomaticallyIfPossible() {
        if (firebaseAuth.currentUser != null) {
            _loginAutomatically.value = true
        }
    }
}