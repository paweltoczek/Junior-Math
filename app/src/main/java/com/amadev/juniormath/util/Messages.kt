package com.amadev.juniormath.util

import android.content.Context
import com.amadev.juniormath.R

sealed class Messages {
    object FieldCantBeEmpty : Messages()
    object InvalidEmail : Messages()
    object EmailAlreadyExists : Messages()
    object PasswordIsToWeak : Messages()
    object FailedToSignIn : Messages()
    object VerifyEmailSent : Messages()
    object LoginFailed : Messages()
}


@FunctionalInterface
interface ProvideMessage {
    val fieldCantBeEmpty: Messages.FieldCantBeEmpty
        get() = Messages.FieldCantBeEmpty

    val invalidEmail: Messages.InvalidEmail
        get() = Messages.InvalidEmail
    val verifyEmailSent: Messages.VerifyEmailSent
        get() = Messages.VerifyEmailSent

    val loginFailed: Messages.LoginFailed
        get() = Messages.LoginFailed

    fun getMessage(message: Messages, context: Context) =
        when (message) {
            is Messages.FieldCantBeEmpty -> context.getString(R.string.fieldCantBeEmpty)
            is Messages.InvalidEmail -> context.getString(R.string.enterValidEmailAddress)
            is Messages.VerifyEmailSent -> context.getString(R.string.pleaseVerifyEmailSent)
            is Messages.LoginFailed -> context.getString(R.string.failedToLogIn)
            else -> ""
        }

}


