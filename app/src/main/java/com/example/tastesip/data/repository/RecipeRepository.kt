package com.example.tastesip.data.repository

import com.example.tastesip.data.api.ApiService
import com.example.tastesip.data.model.Category
import com.example.tastesip.util.Resource

class RecipeRepository(private val apiService: ApiService) {
    suspend fun getMealCategories(): Resource<List<Category>> {
        return try {
            val response = apiService.getMealCategories()
            Resource.Success(response.categories)
        } catch (e: Exception) {
            Resource.Error("An error occurred: ${e.message}")
        }
    }
}