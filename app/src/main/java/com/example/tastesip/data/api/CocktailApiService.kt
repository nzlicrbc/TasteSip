package com.example.tastesip.data.api

import com.example.tastesip.data.model.CocktailCategoryResponse
import com.example.tastesip.data.model.CocktailDetailResponse
import com.example.tastesip.data.model.CocktailListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("list.php?c=list")
    suspend fun getCocktailCategories(): CocktailCategoryResponse

    @GET("filter.php?c=KATEGORI_ADI")
    suspend fun getCocktailsByCategory(@Query("c") category: String): CocktailListResponse

    @GET("lookup.php?i=KOKTEYL_ID")
    suspend fun getCocktailDetails(@Query("i") id: String): CocktailDetailResponse
}