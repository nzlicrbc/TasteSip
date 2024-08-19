package com.example.tastesip.data.repository

import com.example.tastesip.data.api.CocktailApiService
import com.example.tastesip.data.model.Cocktail
import com.example.tastesip.data.model.CocktailCategory
import com.example.tastesip.data.model.CocktailDetail
import com.example.tastesip.util.Resource
import java.io.IOException

class CocktailRepository(private val apiService: CocktailApiService) {

    private suspend fun <T> fetchData(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall())
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("An error occurred: ${e.message}")
        }
    }

    suspend fun getCocktailCategories(): Resource<List<CocktailCategory>> {
        return fetchData { apiService.getCocktailCategories().drinks }
    }

    suspend fun getCocktailsByCategory(category: String): Resource<List<Cocktail>> {
        return fetchData { apiService.getCocktailsByCategory(category).drinks }
    }

    suspend fun getCocktailDetail(id: String): Resource<CocktailDetail?> {
        return fetchData { apiService.getCocktailDetails(id).drinks.firstOrNull() }
    }
}