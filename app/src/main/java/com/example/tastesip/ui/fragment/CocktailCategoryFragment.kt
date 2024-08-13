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
import com.example.tastesip.R
import com.example.tastesip.databinding.FragmentCocktailCategoryBinding
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.ui.adapter.CocktailCategoryAdapter
import com.example.tastesip.ui.viewmodel.CategoryViewModelFactory
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        binding.lottieAnimationView.playAnimation()
        binding.lottieAnimationView.visibility = View.VISIBLE
        binding.recyclerViewCocktailCategories.visibility = View.GONE

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
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.lottieAnimationView.cancelAnimation()
                        binding.recyclerViewCocktailCategories.visibility = View.VISIBLE
                        adapter.submitList(resource.data ?: emptyList())
                    }
                }

                is Resource.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.lottieAnimationView.cancelAnimation()
                    binding.recyclerViewCocktailCategories.visibility = View.VISIBLE
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.recyclerViewCocktailCategories.visibility = View.GONE
                }
            }
        }
    }
}