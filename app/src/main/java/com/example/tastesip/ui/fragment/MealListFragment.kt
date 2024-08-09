package com.example.tastesip.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.databinding.FragmentMealListBinding
import com.example.tastesip.ui.adapter.MealListAdapter
import com.example.tastesip.ui.viewmodel.ListViewModel
import com.example.tastesip.ui.viewmodel.ListViewModelFactory
import com.example.tastesip.ui.viewmodel.RecipeViewModel
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar

class MealListFragment : Fragment() {

    private lateinit var binding: FragmentMealListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: MealListAdapter
    private val args: MealListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealRepository = MealRepository(RetrofitClient.mealApiService)
        val cocktailRepository = CocktailRepository(RetrofitClient.cocktailApiService)
        val viewModelFactory = ListViewModelFactory(mealRepository, cocktailRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)

        setupRecyclerView()
        observeViewModel()
        viewModel.fetchMealsByCategory(args.category)
    }

    private fun setupRecyclerView() {
        adapter = MealListAdapter(emptyList()) { meal ->
            val action = MealListFragmentDirections.actionMealListFragmentToMealDetailFragment(meal.idMeal)
            findNavController().navigate(action)
        }
        binding.recyclerViewMeals.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MealListFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.meals.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        adapter.submitList(it)
                    }
                }
                is Resource.Error -> {
                    Snackbar.make(requireView(), resource.message ?: "Bir hata oluştu", Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                }
            }
        }
    }
}
