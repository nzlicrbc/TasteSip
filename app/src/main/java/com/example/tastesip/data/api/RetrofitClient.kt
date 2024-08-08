package com.example.tastesip.data.api

import com.example.tastesip.util.Constants.COCKTAIL_BASE_URL
import com.example.tastesip.util.Constants.MEAL_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

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

    val mealApiService: MealApiService by lazy {
        mealRetrofit.create(MealApiService::class.java)
    }

    val cocktailApiService: CocktailApiService by lazy {
        cocktailRetrofit.create(CocktailApiService::class.java)
    }
}
