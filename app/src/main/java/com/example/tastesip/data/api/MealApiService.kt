package com.example.tastesip.data.api

import com.example.tastesip.data.model.MealCategoryResponse
import com.example.tastesip.data.model.MealDetailResponse
import com.example.tastesip.data.model.MealListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("categories.php")
    suspend fun getMealCategories(): MealCategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealListResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): MealDetailResponse
}