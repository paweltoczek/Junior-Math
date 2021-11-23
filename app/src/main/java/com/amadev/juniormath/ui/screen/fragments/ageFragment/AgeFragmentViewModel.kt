package com.amadev.juniormath.ui.screen.fragments.ageFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadev.juniormath.util.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgeFragmentViewModel @Inject constructor() : ViewModel() {

    val ageInput = mutableStateOf("")
    private val _popUpMessage = MutableLiveData<String?>()
    val popUpMessage = _popUpMessage

//    fun findGenreNameById(id: Int?): String = Message.values().firstOrNull { it.fieldCantBeEmpty == id }?.nameValue

    fun validateAgeInput() {
        if (ageInput.value.isEmpty()) {
//            _popUpMessage.value = Message.values().first().fieldCantBeEmpty
        }
    }
}