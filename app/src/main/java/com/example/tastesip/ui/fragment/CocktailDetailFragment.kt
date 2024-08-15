package com.example.tastesip.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.navArgs
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.model.CocktailDetail
import com.example.tastesip.data.model.CocktailDetailItem
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.ui.adapter.DetailAdapter
import com.example.tastesip.ui.viewmodel.RecipeViewModel
import com.example.tastesip.ui.viewmodel.RecipeViewModelFactory
import com.example.tastesip.util.Resource

class CocktailDetailFragment : DetailFragment<CocktailDetail, CocktailDetailItem>() {

    private val args: CocktailDetailFragmentArgs by navArgs()
    override val viewModel: RecipeViewModel by viewModels {
        RecipeViewModelFactory(
            MealRepository(RetrofitClient.mealApiService),
            CocktailRepository(RetrofitClient.cocktailApiService)
        )
    }

    override val adapter: DetailAdapter<CocktailDetailItem> = DetailAdapter()

    override fun fetchDetail() {
        viewModel.fetchCocktailDetail(args.cocktailId)
    }

    override fun getDetailLiveData(): LiveData<Resource<CocktailDetail?>> = viewModel.cocktailDetail
    override fun getDetailItemsLiveData(): LiveData<List<CocktailDetailItem>> = viewModel.cocktailDetailItems

    override fun updateUI(detail: CocktailDetail) {
        binding.detailTitleTextView.text = detail.strDrink
        loadImage(detail.strDrinkThumb)
    }
}