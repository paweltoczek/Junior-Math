package com.amadev.juniormath.data.repository

import com.amadev.juniormath.data.model.UserAnswersModel
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseRepository {

    fun getUserAdditionScoreData() : Flow<Result<ArrayList<UserAnswersModel>>>
}