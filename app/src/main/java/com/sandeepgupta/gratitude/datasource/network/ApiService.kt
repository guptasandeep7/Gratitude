package com.sandeepgupta.gratitude.datasource.network

import com.sandeepgupta.gratitude.model.CardModel
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("prod/dailyzen/?version=2")
    suspend fun getCardList(
        @Query("date") date: Long
    ): List<CardModel>

}