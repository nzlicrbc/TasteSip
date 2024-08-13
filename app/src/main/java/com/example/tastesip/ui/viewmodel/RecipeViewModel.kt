package com.example.tastesip.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastesip.data.model.CocktailDetail
import com.example.tastesip.data.model.CocktailDetailItem
import com.example.tastesip.data.model.MealDetail
import com.example.tastesip.data.model.MealDetailItem
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

    private val _cocktailDetailItems = MutableLiveData<List<CocktailDetailItem>>()
    val cocktailDetailItems: LiveData<List<CocktailDetailItem>> = _cocktailDetailItems

    private val _mealDetailItems = MutableLiveData<List<MealDetailItem>>()
    val mealDetailItems: LiveData<List<MealDetailItem>> = _mealDetailItems

    fun fetchMealDetail(id: String) {
        viewModelScope.launch {
            _mealDetail.value = Resource.Loading()
            val result = mealRepository.getMealDetail(id)
            _mealDetail.value = result

            if (result is Resource.Success) {
                result.data?.let { mealDetail ->
                    processMealDetail(mealDetail)
                }
            }
        }
    }

    private fun processMealDetail(mealDetail: MealDetail) {
        val items = mutableListOf<MealDetailItem>()
        items.add(MealDetailItem.Instruction(mealDetail.strInstructions))

        val ingredients = mealDetail.ingredientsAndMeasuresList
            .filter { (ingredient, measure) -> !ingredient.isNullOrBlank() && !measure.isNullOrBlank() }
            .joinToString("\n") { (ingredient, measure) -> "$measure $ingredient" }
        items.add(MealDetailItem.Ingredient(ingredients))

        _mealDetailItems.value = items
    }

    fun fetchCocktailDetail(id: String) {
        viewModelScope.launch {
            _cocktailDetail.value = Resource.Loading()
            val result = cocktailRepository.getCocktailDetail(id)
            _cocktailDetail.value = result

            if (result is Resource.Success) {
                result.data?.let { cocktailDetail ->
                    processCocktailDetail(cocktailDetail)
                }
            }
        }
    }

    private fun processCocktailDetail(cocktailDetail: CocktailDetail) {
        val items = mutableListOf<CocktailDetailItem>()
        items.add(CocktailDetailItem.Instruction(cocktailDetail.strInstructions))

        val ingredients = cocktailDetail.ingredientsAndMeasuresList
            .filter { (ingredient, measure) -> !ingredient.isNullOrBlank() && !measure.isNullOrBlank() }
            .joinToString("\n") { (ingredient, measure) -> "$measure $ingredient" }
        items.add(CocktailDetailItem.Ingredient(ingredients))

        _cocktailDetailItems.value = items
    }
}