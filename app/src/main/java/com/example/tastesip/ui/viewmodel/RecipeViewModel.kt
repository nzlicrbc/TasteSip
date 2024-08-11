package com.example.tastesip.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastesip.data.model.CocktailDetail
import com.example.tastesip.data.model.MealDetail
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.util.Resource
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val mealRepository: MealRepository,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _mealDetail = MutableLiveData<Resource<MealDetail?>>()
    val mealDetail: LiveData<Resource<MealDetail?>> = _mealDetail

    private val _cocktailDetail = MutableLiveData<Resource<CocktailDetail?>>()
    val cocktailDetail: LiveData<Resource<CocktailDetail?>> = _cocktailDetail


    fun fetchMealDetail(id: String) {
        viewModelScope.launch {
            _mealDetail.value = Resource.Loading()
            _mealDetail.value = mealRepository.getMealDetail(id)
        }
    }

    fun fetchCocktailDetail(id: String) {
        viewModelScope.launch {
            _cocktailDetail.value = Resource.Loading()
            _cocktailDetail.value = cocktailRepository.getCocktailDetail(id)

        }
    }
}