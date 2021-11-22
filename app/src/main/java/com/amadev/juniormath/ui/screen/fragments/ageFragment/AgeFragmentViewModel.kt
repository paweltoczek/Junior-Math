package com.amadev.juniormath.ui.screen.fragments.ageFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgeFragmentViewModel @Inject constructor() : ViewModel() {

    val ageInput = mutableStateOf("")
}