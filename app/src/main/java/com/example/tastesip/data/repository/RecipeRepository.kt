package com.example.tastesip.data.repository

import com.example.tastesip.data.api.ApiService
import com.example.tastesip.data.model.Category
import com.example.tastesip.data.model.Cocktail
import com.example.tastesip.data.model.Meal
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

    suspend fun getCocktailCategories(): Resource<List<Category>> {
        return try {
            val response = apiService.getCocktailCategories()
            Resource.Success(response.categories)
        } catch (e: Exception) {
            Resource.Error("An error occurred: ${e.message}")
        }
    }

    suspend fun getMealsByCategory(category: String): Resource<List<Meal>> {
        return try {
            val response = apiService.getMealsByCategory(category)
            Resource.Success(response.meals)
        } catch (e: Exception) {
            Resource.Error("An error occurred: ${e.message}")
        }
    }

    suspend fun getCocktailsByCategory(category: String): Resource<List<Cocktail>> {
        return try {
            val response = apiService.getCocktailsByCategory(category)
            Resource.Success(response.drinks)
        } catch (e: Exception) {
            Resource.Error("An error occurred: ${e.message}")
        }
    }
}