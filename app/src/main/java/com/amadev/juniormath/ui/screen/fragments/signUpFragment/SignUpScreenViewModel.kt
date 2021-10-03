package com.amadev.juniormath.ui.screen.fragments.signUpFragment

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) : ViewModel(), ProvideMessage {

    val emailInput = mutableStateOf("")
    val emailInputErrorTextState = mutableStateOf(false)
    val emailInputErrorTextValue = mutableStateOf("")

    val passwordInput = mutableStateOf("")
    val passwordInputErrorTextState = mutableStateOf(false)
    val passwordInputErrorTextValue = mutableStateOf("")

    val repeatPasswordInput = mutableStateOf("")
    val repeatPasswordInputErrorTextState = mutableStateOf(false)
    val repeatPasswordInputErrorTextValue = mutableStateOf("")

    val signUpButtonState = mutableStateOf(false)

    val verificationEmailSentState = mutableStateOf(false)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    fun onEmailInputChanged(input: String) {
        emailInput.value = input
        emailInputErrorTextState.value = false
    }

    fun onPasswordInputChanged(input: String) {
        passwordInput.value = input
        passwordInputErrorTextState.value = false
    }

    fun onRepeatPasswordInputChanged(input: String) {
        repeatPasswordInput.value = input
        repeatPasswordInputErrorTextState.value = false
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
            passwordInput.value.trim() != repeatPasswordInput.value.trim() -> {
                repeatPasswordInputErrorTextState.value = true
                repeatPasswordInputErrorTextValue.value = getMessage(passwordsMustBeSame, context)
            }
            else -> {
                signUpButtonState.value = true
                createUserWithEmailAndPassword()
            }
        }
    }

    private fun createUserWithEmailAndPassword() {
        firebaseAuth.createUserWithEmailAndPassword(
            emailInput.value.trim(),
            passwordInput.value.trim()
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    sendVerificationEmail()
                    verificationEmailSentState.value = true
                    signUpButtonState.value = false
                    _popUpMessage.value = getMessage(verificationEmailSent, context)

                } else if (it.isSuccessful.not()) {
                    try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        signUpButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: FirebaseAuthUserCollisionException) {
                        signUpButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: FirebaseAuthException) {
                        signUpButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: TimeoutException) {
                        signUpButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: Exception) {
                        signUpButtonState.value = false
                        _popUpMessage.value = e.message
                    }
                }
            }
    }

    private fun sendVerificationEmail() = firebaseAuth.currentUser?.sendEmailVerification()
}