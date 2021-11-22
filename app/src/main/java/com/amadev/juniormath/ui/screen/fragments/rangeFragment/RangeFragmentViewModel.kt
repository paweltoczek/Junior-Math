package com.amadev.juniormath.ui.screen.fragments.rangeFragment

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.Categories
import com.amadev.juniormath.util.ProvideMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class RangeFragmentViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel(), ProvideMessage {

    val fromRangeInput = mutableStateOf("1")
    val toRangeInput = mutableStateOf("10")

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage

    private val _goToPracticeFragment = MutableLiveData<Boolean>()
    val goToPracticeFragment = _goToPracticeFragment

    private val category = mutableStateOf<String?>("")

    fun onFromRangeInputChanged(input: String) {
        fromRangeInput.value = input
            .replace(".", "")
            .replace(",", "")
            .take(4)
    }

    fun onToRangeInputChanged(input: String) {
        toRangeInput.value = input
            .replace(".", "")
            .replace(",", "")
            .take(6)
    }

    fun validateInput() {
        val firstNumber = fromRangeInput.value
        val secondNumber = toRangeInput.value

        if (firstNumber.isEmpty() || secondNumber.isEmpty()) {
            _popUpMessage.value = getMessage(fieldCantBeEmpty, context)
        } else if (firstNumber > secondNumber) {
            _popUpMessage.value = getMessage(fromRangeCantBeLower, context)
        } else if (this.category.value == Categories.Division.name && firstNumber == "0") {
            _popUpMessage.value = getMessage(firstNumberCantBeZero, context)
        } else if (this.category.value == Categories.Division.name && secondNumber == "0") {
            _popUpMessage.value = getMessage(secondNumberCantBeZero, context)
        } else if (firstNumber == "0" && secondNumber == "0") {
            _popUpMessage.value = getMessage(bothNumbersCantBeZero, context)
        } else {
            _goToPracticeFragment.value = true
        }
    }

    fun setUpCategory(category: String?) {
        this.category.value = category
    }


}