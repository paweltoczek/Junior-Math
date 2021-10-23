package com.amadev.juniormath.data.repository

import com.amadev.juniormath.data.model.UserAnswersModel
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseRepository {

    fun getUserAdditionScoreData() : Flow<Result<UserAnswersModel>>

    fun getUserSubtractionScoreData() : Flow<Result<ArrayList<UserAnswersModel>>>

    fun getUserMultiplicationScoreData() : Flow<Result<ArrayList<UserAnswersModel>>>

    fun getUserDivisionScoreData() : Flow<Result<ArrayList<UserAnswersModel>>>
}