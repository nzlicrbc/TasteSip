package com.example.tastesip.data.repository

import com.example.tastesip.data.api.MealApiService
import com.example.tastesip.data.model.Meal
import com.example.tastesip.data.model.MealCategory
import com.example.tastesip.data.model.MealDetail
import com.example.tastesip.util.Resource
import java.io.IOException

class MealRepository(private val apiService: MealApiService) {

    private suspend fun <T> fetchData(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall())
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("An error occurred: ${e.message}")
        }
    }

    suspend fun getMealCategories(): Resource<List<MealCategory>> {
        return fetchData { apiService.getMealCategories().categories }
    }

    suspend fun getMealsByCategory(category: String): Resource<List<Meal>> {
        return fetchData { apiService.getMealsByCategory(category).meals }
    }

    suspend fun getMealDetail(id: String): Resource<MealDetail?> {
        return fetchData { apiService.getMealDetails(id).meals.firstOrNull() }
    }

}