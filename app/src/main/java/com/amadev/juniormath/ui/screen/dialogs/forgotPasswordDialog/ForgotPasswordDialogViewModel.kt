package com.amadev.juniormath.ui.screen.dialogs.forgotPasswordDialog

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.ProvideMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordDialogViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel(), ProvideMessage {

    val emailInput = mutableStateOf("")
    val emailInputErrorTextState = mutableStateOf(false)
    val emailInputErrorTextValue = mutableStateOf("")
    val sendEmailButtonState = mutableStateOf(false)

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    fun onEmailInputChanged(input: String) {
        emailInput.value = input
        emailInputErrorTextState.value = false
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

            else -> {
                sendEmailButtonState.value = true
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
        firebaseAuth.sendPasswordResetEmail(emailInput.value.trim())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    sendEmailButtonState.value = false
                    _popUpMessage.value = getMessage(emailSent, context)
                } else if (it.isSuccessful.not()) {
                    try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthEmailException) {
                        sendEmailButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: FirebaseAuthInvalidUserException) {
                        sendEmailButtonState.value = false
                        _popUpMessage.value = e.message
                    } catch (e: TimeoutException) {
                        sendEmailButtonState.value = false
                        _popUpMessage.value = e.message
                    }
                }
            }
    }
}