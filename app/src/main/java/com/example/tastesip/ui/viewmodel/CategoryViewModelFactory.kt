package com.example.tastesip.ui.viewmodel

import CategoryViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository

class CategoryViewModelFactory(
    private val mealRepository: MealRepository,
    private val cocktailRepository: CocktailRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(mealRepository, cocktailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
