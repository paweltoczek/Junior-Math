package com.amadev.juniormath.ui.screen.fragments.termsOfUsePrivacyPolicy

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsOfUsePrivacyPolicyViewModel @Inject constructor() : ViewModel() {

    val noticeUnderstood = mutableStateOf(false)
    val privacyPolicyAccepted = mutableStateOf(false)

}