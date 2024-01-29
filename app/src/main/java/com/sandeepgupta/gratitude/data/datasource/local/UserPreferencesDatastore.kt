package com.sandeepgupta.gratitude.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preference"
)

val KEY_DATE = stringPreferencesKey("DATE")