package com.example.tastesip.ui.fragment

import CategoryViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.model.CocktailCategory
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.ui.adapter.CategoryAdapter
import com.example.tastesip.ui.viewmodel.CategoryViewModelFactory
import com.example.tastesip.util.Resource

class CocktailCategoryFragment : CategoryFragment<CocktailCategory>() {
    override val viewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(
            MealRepository(RetrofitClient.mealApiService),
            CocktailRepository(RetrofitClient.cocktailApiService)
        )
    }

    override fun setupRecyclerView() {
        adapter = CategoryAdapter { category ->
            val action = CocktailCategoryFragmentDirections.actionCocktailCategoryFragmentToCocktailListFragment(category.strCategory)
            findNavController().navigate(action)
        }
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@CocktailCategoryFragment.adapter
        }
    }

    override fun getLiveData(): LiveData<Resource<List<CocktailCategory>>> = viewModel.cocktailCategories
}