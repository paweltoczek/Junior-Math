package com.amadev.juniormath.util

import android.app.Application
import android.content.Context
import com.amadev.juniormath.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.AccessController.getContext


enum class Example(private val mResourceId: Int) {
    FieldCantBeEmpty(R.string.fieldCantBeEmpty)

//    override fun toString(): String {
//        return Application.get.getString(mResourceId)
//    }
}

enum class Message(
    val fieldCantBeEmpty : Int = R.string.fieldCantBeEmpty
)


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
    object SomethingWentWrong : Messages()
    object YouNeedToAcceptTermsOfUseAndPrivacyPolicy : Messages()
    object FromRangeCantBeLower : Messages()
    object FirstNumberCantBeZero : Messages()
    object SecondNumberCantBeZero : Messages()
    object BothNumbersCantBeZero : Messages()
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

    val pleaseVerifyYourEmail: Messages.PleaseVerifyEmailSent
        get() = Messages.PleaseVerifyEmailSent

    val selectYourAnswer: Messages.SelectYourAnswer
        get() = Messages.SelectYourAnswer

    val dataSaved: Messages.DataSaved
        get() = Messages.DataSaved

    val somethingWentWrong: Messages.SomethingWentWrong
        get() = Messages.SomethingWentWrong

    val acceptTermsOfUse: Messages.YouNeedToAcceptTermsOfUseAndPrivacyPolicy
        get() = Messages.YouNeedToAcceptTermsOfUseAndPrivacyPolicy

    val fromRangeCantBeLower: Messages.FromRangeCantBeLower
        get() = Messages.FromRangeCantBeLower

    val firstNumberCantBeZero : Messages.FirstNumberCantBeZero
        get() = Messages.FirstNumberCantBeZero

    val secondNumberCantBeZero : Messages.FirstNumberCantBeZero
        get() = Messages.FirstNumberCantBeZero

    val bothNumbersCantBeZero : Messages.BothNumbersCantBeZero
        get() = Messages.BothNumbersCantBeZero

    fun getMessage(message: Messages, context: Context) =
        when (message) {
            is Messages.FieldCantBeEmpty -> context.getString(R.string.fieldCantBeEmpty)
            is Messages.InvalidEmail -> context.getString(R.string.enterValidEmailAddress)
            is Messages.PleaseVerifyEmailSent -> context.getString(R.string.pleaseVerifyEmailSent)
            is Messages.LoginFailed -> context.getString(R.string.failedToLogIn)
            is Messages.PasswordsMustBeSame -> context.getString(R.string.passwordsMustBeSame)
            is Messages.VerificationEmailSent -> context.getString(R.string.verificationEmailSent)
            is Messages.FailedToCreateAccount -> context.getString(R.string.failedToCreateAccount)
            is Messages.PleaseVerifyEmailSent -> context.getString(R.string.pleaseVerifyYourEmail)
            is Messages.EmailSent -> context.getString(R.string.emailSent)
            is Messages.SelectYourAnswer -> context.getString(R.string.selectYourAnswer)
            is Messages.DataSaved -> context.getString(R.string.dataSaved)
            is Messages.SomethingWentWrong -> context.getString(R.string.somethingWentWrong)
            is Messages.YouNeedToAcceptTermsOfUseAndPrivacyPolicy -> context.getString(R.string.acceptTermOfUseRequired)
            is Messages.FromRangeCantBeLower -> context.getString(R.string.fromRangeCantBeLowerThanToRange)
            is Messages.FirstNumberCantBeZero -> context.getString(R.string.firstNumberCantBeZero)
            is Messages.SecondNumberCantBeZero -> context.getString(R.string.secondNumebrCantBeZero)
            is Messages.BothNumbersCantBeZero -> context.getString(R.string.bothNumbersCantBeZero)
        }


}


