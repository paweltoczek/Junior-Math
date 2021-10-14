package com.amadev.juniormath.ui.screen.fragments.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadev.juniormath.data.firebase.FirebaseService
import com.amadev.juniormath.data.model.UserAnswersModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsFragmentViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    private val _popUpMessage = MutableLiveData<String>()
    val popUpMessage = _popUpMessage
    private val _additionData = MutableLiveData<ArrayList<UserAnswersModel>>()
    val additionData = _additionData

    fun getAdditionData() {
        val additionArrayList = ArrayList<UserAnswersModel>()
        viewModelScope.launch(Dispatchers.IO) {
            firebaseService.getAdditionData(firebaseService.addition)
                ?.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { data ->
                            data.getValue(UserAnswersModel::class.java)?.let {
                                additionArrayList.add(it)
                            }
                        }
                        _additionData.postValue(additionArrayList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _popUpMessage.postValue(error.message)
                    }
                })
        }
    }


}