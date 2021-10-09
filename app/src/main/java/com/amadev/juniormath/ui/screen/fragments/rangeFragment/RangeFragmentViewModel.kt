package com.amadev.juniormath.ui.screen.fragments.rangeFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RangeFragmentViewModel @Inject constructor() : ViewModel() {

    val fromRangeInput = mutableStateOf("1")
    val toRangeInput = mutableStateOf("10")

    fun onFromRangeInputChanged(input: String) {
        fromRangeInput.value = input
            .replace(".", "")
            .replace(",", "")
            .take(6)
    }

    fun onToRangeInputChanged(input: String) {
        toRangeInput.value = input
            .replace(".", "")
            .replace(",", "")
            .take(4)
    }


}