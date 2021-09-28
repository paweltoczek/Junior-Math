package com.amadev.juniormath.util

import android.content.Context
import com.amadev.juniormath.R
import dagger.hilt.android.qualifiers.ApplicationContext


sealed class Messages {
    object FieldCantBeEmpty : Messages()
    object InvalidEmail : Messages()
    object EmailAlreadyExists : Messages()
    object PasswordIsToWeak : Messages()
    object PasswordsMustBeSame : Messages()
    object PleaseVerifyEmailSent : Messages()
    object VerificationEmailSent : Messages()
    object FailedToCreateAccount : Messages()
    object LoginFailed : Messages()
}


@FunctionalInterface
interface ProvideMessage {
    val fieldCantBeEmpty: Messages.FieldCantBeEmpty
        get() = Messages.FieldCantBeEmpty

    val invalidEmail: Messages.InvalidEmail
        get() = Messages.InvalidEmail

    val verifyEmailSent: Messages.PleaseVerifyEmailSent
        get() = Messages.PleaseVerifyEmailSent

    val passwordsMustBeSame: Messages.PasswordsMustBeSame
        get() = Messages.PasswordsMustBeSame

    val verificationEmailSent: Messages.VerificationEmailSent
        get() = Messages.VerificationEmailSent

    fun getMessage(message: Messages, context: Context) =
        when (message) {
            is Messages.FieldCantBeEmpty -> context.getString(R.string.fieldCantBeEmpty)
            is Messages.InvalidEmail -> context.getString(R.string.enterValidEmailAddress)
            is Messages.PleaseVerifyEmailSent -> context.getString(R.string.pleaseVerifyEmailSent)
            is Messages.LoginFailed -> context.getString(R.string.failedToLogIn)
            is Messages.PasswordsMustBeSame -> context.getString(R.string.passwordsMustBeSame)
            is Messages.VerificationEmailSent -> context.getString(R.string.verificationEmailSent)
            is Messages.FailedToCreateAccount -> context.getString(R.string.failedToCreateAccount)
            else -> ""
        }
}


