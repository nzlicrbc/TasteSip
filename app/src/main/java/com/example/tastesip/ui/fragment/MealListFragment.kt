package com.example.tastesip.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.model.Meal
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.ui.adapter.ListAdapter
import com.example.tastesip.ui.viewmodel.ListViewModel
import com.example.tastesip.ui.viewmodel.ListViewModelFactory
import com.example.tastesip.util.Resource

class MealListFragment : ListFragment<Meal>() {
    override val viewModel: ListViewModel by viewModels {
        ListViewModelFactory(
            MealRepository(RetrofitClient.mealApiService),
            CocktailRepository(RetrofitClient.cocktailApiService)
        )
    }
    private val args: MealListFragmentArgs by navArgs()

    override fun setupRecyclerView() {
        adapter = ListAdapter { meal ->
            val action = MealListFragmentDirections.actionMealListFragmentToMealDetailFragment(meal.idMeal)
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@MealListFragment.adapter
        }
    }

    override fun fetchData() {
        viewModel.fetchMealsByCategory(args.category)
    }

    override fun getLiveData(): LiveData<Resource<List<Meal>>> = viewModel.meals
}