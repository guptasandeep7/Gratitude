package com.sandeepgupta.gratitude.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val DAILY_ZEN_TABLE_NAME = "daily_zen_table"

@Entity(tableName = DAILY_ZEN_TABLE_NAME)
data class CardModel(
    val articleUrl: String,
    val author: String,
    val bgImageUrl: String,
    val dzImageUrl: String,
    val dzType: String,
    val language: String,
    val primaryCTAText: String,
    val sharePrefix: String,
    val text: String,
    val theme: String,
    val themeTitle: String,
    val type: String,
    @PrimaryKey
    val uniqueId: String
)