package com.example.tastesip.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.model.Cocktail
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.ui.adapter.ListAdapter
import com.example.tastesip.ui.viewmodel.ListViewModel
import com.example.tastesip.ui.viewmodel.ListViewModelFactory
import com.example.tastesip.util.Resource

class CocktailListFragment : ListFragment<Cocktail>() {
    override val viewModel: ListViewModel by viewModels {
        ListViewModelFactory(
            MealRepository(RetrofitClient.mealApiService),
            CocktailRepository(RetrofitClient.cocktailApiService)
        )
    }
    private val args: CocktailListFragmentArgs by navArgs()

    override fun setupRecyclerView() {
        adapter = ListAdapter { cocktail ->
            val action = CocktailListFragmentDirections.actionCocktailListFragmentToCocktailDetailFragment(cocktail.idDrink)
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@CocktailListFragment.adapter
        }
    }

    override fun fetchData() {
        viewModel.fetchCocktailsByCategory(args.category)
    }

    override fun getLiveData(): LiveData<Resource<List<Cocktail>>> = viewModel.drinks
}