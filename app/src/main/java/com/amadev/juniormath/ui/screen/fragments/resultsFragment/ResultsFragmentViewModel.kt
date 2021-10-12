package com.amadev.juniormath.ui.screen.fragments.resultsFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultsFragmentViewModel@Inject constructor() : ViewModel() {

    val userAnswers = mutableStateOf(0)

    fun handleUserAnswers(answers : Int) {
        userAnswers.value = answers
    }
}