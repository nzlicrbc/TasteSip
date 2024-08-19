package com.example.tastesip.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.navArgs
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.model.MealDetail
import com.example.tastesip.data.model.MealDetailItem
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.ui.adapter.DetailAdapter
import com.example.tastesip.ui.viewmodel.RecipeViewModel
import com.example.tastesip.ui.viewmodel.RecipeViewModelFactory
import com.example.tastesip.util.Resource

class MealDetailFragment : DetailFragment<MealDetail, MealDetailItem>() {

    private val args: MealDetailFragmentArgs by navArgs()
    override val viewModel: RecipeViewModel by viewModels {
        RecipeViewModelFactory(
            MealRepository(RetrofitClient.mealApiService),
            CocktailRepository(RetrofitClient.cocktailApiService)
        )
    }
    override val adapter: DetailAdapter<MealDetailItem> = DetailAdapter()

    override fun fetchDetail() {
        viewModel.fetchMealDetail(args.mealId)
    }

    override fun getDetailLiveData(): LiveData<Resource<MealDetail?>> = viewModel.mealDetail
    override fun getDetailItemsLiveData(): LiveData<List<MealDetailItem>> = viewModel.mealDetailItems

    override fun updateUI(detail: MealDetail) {
        binding.detailTitleTextView.text = detail.strMeal
        loadImage(detail.strMealThumb)
    }
}