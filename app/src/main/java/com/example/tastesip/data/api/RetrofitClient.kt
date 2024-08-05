package com.example.tastesip.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val MEAL_BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    private const val COCKTAIL_BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    private val mealRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MEAL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val cocktailRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(COCKTAIL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mealApiService: ApiService by lazy {
        mealRetrofit.create(ApiService::class.java)
    }

    val cocktailApiService: ApiService by lazy {
        cocktailRetrofit.create(ApiService::class.java)
    }
}