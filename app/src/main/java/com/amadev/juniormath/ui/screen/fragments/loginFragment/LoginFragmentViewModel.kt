package com.amadev.juniormath.ui.screen.fragments.loginFragment

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) : ViewModel(), ProvideMessage {

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
        firebaseAuth.signInWithEmailAndPassword(
            emailInput.value.trim(),
            passwordInput.value.trim()
        )
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    if (currentUser != null) {
                        if (currentUser.isEmailVerified.not()) {
                            _popUpMessage.value = getMessage(verifyEmailSent, context)
                            loginButtonState.value = false
                        } else {
                            loginAutomatically.value = true
                            loginButtonState.value = false
                        }
                    }
                } else if (it.isSuccessful.not()) {
                    try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthEmailException) {
                        loginButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: FirebaseAuthException) {
                        loginButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: FirebaseAuthInvalidUserException) {
                        loginButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: TimeoutException) {
                        loginButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: FirebaseTooManyRequestsException) {
                        loginButtonState.value = false
                        _popUpMessage.value = e.message
                    }
                }
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