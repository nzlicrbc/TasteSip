package com.example.tastesip.data.model

sealed class CocktailDetailItem {
    data class Instruction(val text: String) : CocktailDetailItem()
    data class Ingredient(val text: String) : CocktailDetailItem()
}

sealed class MealDetailItem {
    data class Instruction(val text: String) : MealDetailItem()
    data class Ingredient(val text: String) : MealDetailItem()
}