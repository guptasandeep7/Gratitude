package com.sandeepgupta.gratitude.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sandeepgupta.gratitude.domain.model.CardModel
import com.sandeepgupta.gratitude.domain.model.DAILY_ZEN_TABLE_NAME

@Dao
interface DailyZenDao {

    @Insert
    suspend fun insertDailyZenList(cardList: List<CardModel>)

    @Query("SELECT * FROM $DAILY_ZEN_TABLE_NAME")
    fun getDailyZenList(): List<CardModel>

    @Query("DELETE FROM $DAILY_ZEN_TABLE_NAME")
    suspend fun deleteDailyZenList()

    @Query("SELECT COUNT(*) FROM $DAILY_ZEN_TABLE_NAME")
    suspend fun isEmpty(): Int
}