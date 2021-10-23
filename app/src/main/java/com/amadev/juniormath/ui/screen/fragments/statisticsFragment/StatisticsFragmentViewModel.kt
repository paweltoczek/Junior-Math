package com.amadev.juniormath.ui.screen.fragments.statisticsFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.model.UserAnswersModel
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsFragmentViewModel @Inject constructor(
    private val _realTimeDataBaseRepository : RealtimeDatabaseRepositoryImpl
) : ViewModel() {

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage
    private val _additionData = MutableLiveData<ArrayList<UserAnswersModel>>()
    val additionData = _additionData

    @ExperimentalCoroutinesApi
    fun getUserScoreData () {
        viewModelScope.launch {
            _realTimeDataBaseRepository.getUserAdditionScoreData().collect {
                when{
                    it.isSuccess -> {
                        val list = it.getOrNull()
                        Log.e("list", list.toString())
                    }
                    it.isFailure -> {
                        val exception = it.exceptionOrNull()?.message
                        _popUpMessage.postValue(exception)
                    }
                }
            }
        }
    }


}