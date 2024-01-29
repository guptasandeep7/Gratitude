package com.sandeepgupta.gratitude.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.sandeepgupta.gratitude.data.datasource.local.DailyZenDao
import com.sandeepgupta.gratitude.data.datasource.local.KEY_DATE
import com.sandeepgupta.gratitude.data.datasource.network.ApiService
import com.sandeepgupta.gratitude.domain.model.CardModel
import com.sandeepgupta.gratitude.domain.repo.Repository
import com.sandeepgupta.gratitude.util.ApiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dailyZenDao: DailyZenDao,
    private val userDataStorePreferences: DataStore<Preferences>
) : Repository {

    override fun fetchFromRemoteAndSave(date: Long): Flow<ApiState<List<CardModel>>> = flow {
        emit(ApiState.Loading())
        try {
            val response = apiService.getCardList(date)
            Log.d("response", "getCardListFromRoomDb: $response")
            deleteAllDailyZen()
            insertDailyZenList(response)
            emit(ApiState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiState.Error(e.message.toString()))
        }
    }

    override fun getCardListFromRoomDb(): Flow<List<CardModel>> = flow {
        emit(dailyZenDao.getDailyZenList())
    }

    override suspend fun insertDailyZenList(dailyZenList: List<CardModel>) {
        dailyZenDao.insertDailyZenList(dailyZenList)
    }

    override suspend fun deleteAllDailyZen() {
        dailyZenDao.deleteDailyZenList()
    }

    override suspend fun getDate(): LocalDate {
        val storedValue =
            userDataStorePreferences.data.firstOrNull {
                it.contains(KEY_DATE)
            }?.get(KEY_DATE)
        return LocalDate.parse(storedValue)
    }

    override suspend fun setDate(date: LocalDate) {
        userDataStorePreferences.edit { preferences ->
            preferences[KEY_DATE] =
                date.toString()
        }
    }

    override suspend fun roomDbIsEmpty(): Boolean {
        return dailyZenDao.isEmpty() == 0
    }

}