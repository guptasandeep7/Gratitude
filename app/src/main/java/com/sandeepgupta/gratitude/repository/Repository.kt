package com.sandeepgupta.gratitude.repository

import com.sandeepgupta.gratitude.model.CardModel
import com.sandeepgupta.gratitude.util.ApiState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface Repository {

    fun getCardListFromRoomDb(): Flow<List<CardModel>>

    suspend fun insertDailyZenList(dailyZenList: List<CardModel>)

    suspend fun deleteAllDailyZen()

    fun fetchFromRemoteAndSave(date: Long): Flow<ApiState<List<CardModel>>>

    suspend fun getDate(): LocalDate

    suspend fun setDate(date: LocalDate)

    suspend fun roomDbIsEmpty(): Boolean
}