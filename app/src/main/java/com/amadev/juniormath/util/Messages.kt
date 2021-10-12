package com.amadev.juniormath.util

import android.content.Context
import com.amadev.juniormath.R


sealed class Messages {
    object FieldCantBeEmpty : Messages()
    object InvalidEmail : Messages()
    object PasswordsMustBeSame : Messages()
    object PleaseVerifyEmailSent : Messages()
    object VerificationEmailSent : Messages()
    object FailedToCreateAccount : Messages()
    object EmailSent : Messages()
    object LoginFailed : Messages()
    object SelectYourAnswer : Messages()
    object DataSaved : Messages()
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

    val emailSent: Messages.EmailSent
        get() = Messages.EmailSent

    val pleaseVerifyEmailSent: Messages.PleaseVerifyEmailSent
        get() = Messages.PleaseVerifyEmailSent

    val selectYourAnswer: Messages.SelectYourAnswer
        get() = Messages.SelectYourAnswer

    val dataSaved: Messages.DataSaved
        get() = Messages.DataSaved

    fun getMessage(message: Messages, context: Context) =
        when (message) {
            is Messages.FieldCantBeEmpty -> context.getString(R.string.fieldCantBeEmpty)
            is Messages.InvalidEmail -> context.getString(R.string.enterValidEmailAddress)
            is Messages.PleaseVerifyEmailSent -> context.getString(R.string.pleaseVerifyEmailSent)
            is Messages.LoginFailed -> context.getString(R.string.failedToLogIn)
            is Messages.PasswordsMustBeSame -> context.getString(R.string.passwordsMustBeSame)
            is Messages.VerificationEmailSent -> context.getString(R.string.verificationEmailSent)
            is Messages.FailedToCreateAccount -> context.getString(R.string.failedToCreateAccount)
            is Messages.EmailSent -> context.getString(R.string.emailSent)
            is Messages.PleaseVerifyEmailSent -> context.getString(R.string.pleaseVerifyEmailSent)
            is Messages.SelectYourAnswer -> context.getString(R.string.selectYourAnswer)
            is Messages.DataSaved -> context.getString(R.string.dataSaved)
        }


}


