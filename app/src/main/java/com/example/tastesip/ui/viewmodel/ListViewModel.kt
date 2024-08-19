package com.example.tastesip.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tastesip.data.model.Cocktail
import com.example.tastesip.data.model.Meal
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.util.Resource
import kotlinx.coroutines.launch

class ListViewModel(
    private val mealRepository: MealRepository,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _meals = MutableLiveData<Resource<List<Meal>>>()
    val meals: LiveData<Resource<List<Meal>>> = _meals

    private val _drinks = MutableLiveData<Resource<List<Cocktail>>>()
    val drinks: LiveData<Resource<List<Cocktail>>> = _drinks

    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            _meals.value = Resource.Loading()
            _meals.value = mealRepository.getMealsByCategory(category)
        }
    }

    fun fetchCocktailsByCategory(category: String) {
        viewModelScope.launch {
            _drinks.value = Resource.Loading()
            _drinks.value = cocktailRepository.getCocktailsByCategory(category)
        }
    }
}