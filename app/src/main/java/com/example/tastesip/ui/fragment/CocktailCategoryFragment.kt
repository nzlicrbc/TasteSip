package com.example.tastesip.ui.fragment

import CategoryViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tastesip.databinding.FragmentCocktailCategoryBinding
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.ui.adapter.CocktailCategoryAdapter
import com.example.tastesip.ui.viewmodel.CategoryViewModelFactory
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar

class CocktailCategoryFragment : Fragment() {

    private lateinit var binding: FragmentCocktailCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: CocktailCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCocktailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = CocktailCategoryAdapter { category ->
            val action =
                CocktailCategoryFragmentDirections.actionCocktailCategoryFragmentToCocktailListFragment(
                    category.strCategory
                )
            findNavController().navigate(action)
        }
        binding.recyclerViewCocktailCategories.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@CocktailCategoryFragment.adapter
        }
    }

    private fun setupViewModel() {
        val mealRepository = MealRepository(RetrofitClient.mealApiService)
        val cocktailRepository = CocktailRepository(RetrofitClient.cocktailApiService)
        val viewModelFactory = CategoryViewModelFactory(mealRepository, cocktailRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CategoryViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.cocktailCategories.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    adapter.submitList(resource.data ?: emptyList())
                }

                is Resource.Error -> {
                    Snackbar.make(
                        requireView(),
                        resource.message ?: "Bir hata oluştu",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                }
            }
        }
    }
}