package com.example.tastesip.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastesip.data.model.Cocktail
import com.example.tastesip.data.model.Meal
import com.example.tastesip.data.repository.RecipeRepository
import com.example.tastesip.util.Resource
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _meals = MutableLiveData<Resource<List<Meal>>>()
    val meals: LiveData<Resource<List<Meal>>> = _meals

    private val _cocktails = MutableLiveData<Resource<List<Cocktail>>>()
    val cocktails: LiveData<Resource<List<Cocktail>>> = _cocktails

    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            _meals.value = Resource.Loading()
            _meals.value = repository.getMealsByCategory(category)
        }
    }

    fun fetchCocktailsByCategory(category: String) {
        viewModelScope.launch {
            _cocktails.value = Resource.Loading()
            _cocktails.value = repository.getCocktailsByCategory(category)
        }
    }
}