package com.sandeepgupta.gratitude.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sandeepgupta.gratitude.domain.model.CardModel

const val DATABASE_NAME = "daily_zen_database"

@Database(entities = [(CardModel::class)], version = 1, exportSchema = false)
abstract class DailyZenRoomDatabase : RoomDatabase() {

    abstract fun dailyZenDao(): DailyZenDao
}