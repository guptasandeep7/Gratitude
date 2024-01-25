package com.sandeepgupta.gratitude.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.sandeepgupta.gratitude.BuildConfig
import com.sandeepgupta.gratitude.datasource.local.DATABASE_NAME
import com.sandeepgupta.gratitude.datasource.local.DailyZenDao
import com.sandeepgupta.gratitude.datasource.local.DailyZenRoomDatabase
import com.sandeepgupta.gratitude.datasource.local.userDataStore
import com.sandeepgupta.gratitude.datasource.network.ApiService
import com.sandeepgupta.gratitude.repository.ConnectivityRepository
import com.sandeepgupta.gratitude.repository.Repository
import com.sandeepgupta.gratitude.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRepository(
        apiService: ApiService,
        dailyZenDao: DailyZenDao,
        userDataStorePreferences: DataStore<Preferences>
    ): Repository =
        RepositoryImpl(apiService, dailyZenDao, userDataStorePreferences)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): DailyZenRoomDatabase {
        return Room.databaseBuilder(
            applicationContext,
            DailyZenRoomDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesDailyZenDao(dailyZenRoomDatabase: DailyZenRoomDatabase): DailyZenDao {
        return dailyZenRoomDatabase.dailyZenDao()
    }

    @Provides
    fun providesDatastorePreference(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
        return applicationContext.userDataStore
    }

    @Provides
    fun providesConnectivityRepository(@ApplicationContext context: Context): ConnectivityRepository {
        return ConnectivityRepository(context)
    }
}