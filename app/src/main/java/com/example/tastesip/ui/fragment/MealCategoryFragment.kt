package com.example.tastesip.ui.fragment

import CategoryViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.databinding.FragmentMealCategoryBinding
import com.example.tastesip.ui.adapter.MealCategoryAdapter
import com.example.tastesip.ui.viewmodel.CategoryViewModelFactory
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar

class MealCategoryFragment : Fragment() {

    private lateinit var binding: FragmentMealCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: MealCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = MealCategoryAdapter(emptyList()) { category ->
            val action =
                MealCategoryFragmentDirections.actionMealCategoryFragmentToMealListFragment(category.strCategory)
            findNavController().navigate(action)
        }
        with(binding) {
            recyclerViewMealCategories.layoutManager = LinearLayoutManager(context)
            recyclerViewMealCategories.adapter = adapter
        }
    }

    private fun setupViewModel() {
        val mealRepository = MealRepository(RetrofitClient.mealApiService)
        val cocktailRepository = CocktailRepository(RetrofitClient.cocktailApiService)
        val viewModelFactory = CategoryViewModelFactory(mealRepository, cocktailRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CategoryViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.mealCategories.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    adapter.submitList(resource.data ?: emptyList())
                }

                is Resource.Error -> {
                    Snackbar.make(
                        requireView(),
                        resource.message ?: "An error occurred",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                }
            }
        }
    }
}