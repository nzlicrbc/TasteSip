package com.example.tastesip.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastesip.data.model.Category
import com.example.tastesip.data.repository.RecipeRepository
import com.example.tastesip.util.Resource
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _mealCategories = MutableLiveData<Resource<List<Category>>>()
    val mealCategories: LiveData<Resource<List<Category>>> = _mealCategories

    fun fetchMealCategories() {
        viewModelScope.launch {
            _mealCategories.value = Resource.Loading()
            _mealCategories.value = repository.getMealCategories()
        }
    }

    private val _cocktailCategories = MutableLiveData<Resource<List<Category>>>()
    val cocktailCategories: LiveData<Resource<List<Category>>> = _cocktailCategories

    fun fetchCocktailCategories() {
        viewModelScope.launch {
            _cocktailCategories.value = Resource.Loading()
            _cocktailCategories.value = repository.getCocktailCategories()
        }
    }
}
