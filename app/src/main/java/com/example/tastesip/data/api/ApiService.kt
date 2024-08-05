package com.example.tastesip.data.api

import com.example.tastesip.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        private const val MEAL_BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
        private const val COCKTAIL_BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    }

    // Meal API calls
    @GET("${MEAL_BASE_URL}list.php")
    suspend fun getMealCategories(@Query("c") list: String = "list"): CategoryResponse

    @GET("${MEAL_BASE_URL}filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealListResponse

    @GET("${MEAL_BASE_URL}lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): MealDetailResponse

    // Cocktail API calls
    @GET("${COCKTAIL_BASE_URL}list.php")
    suspend fun getCocktailCategories(@Query("c") list: String = "list"): CategoryResponse

    @GET("${COCKTAIL_BASE_URL}filter.php")
    suspend fun getCocktailsByCategory(@Query("c") category: String): CocktailListResponse

    @GET("${COCKTAIL_BASE_URL}lookup.php")
    suspend fun getCocktailDetails(@Query("i") id: String): CocktailDetailResponse
}