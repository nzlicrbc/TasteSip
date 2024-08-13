package com.example.tastesip.ui.fragment

import CategoryViewModel
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.R
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.databinding.FragmentMealCategoryBinding
import com.example.tastesip.ui.adapter.MealCategoryAdapter
import com.example.tastesip.ui.viewmodel.CategoryViewModelFactory
import com.example.tastesip.ui.viewmodel.ListViewModel
import com.example.tastesip.ui.viewmodel.ListViewModelFactory
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MealCategoryFragment : Fragment() {

    private lateinit var binding: FragmentMealCategoryBinding
    private lateinit var adapter: MealCategoryAdapter
    private val viewModel: CategoryViewModel by viewModels(factoryProducer = {
        CategoryViewModelFactory(
            MealRepository(RetrofitClient.mealApiService),
            CocktailRepository(RetrofitClient.cocktailApiService)
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimationView.playAnimation()
        binding.lottieAnimationView.visibility = View.VISIBLE
        binding.recyclerViewMealCategories.visibility = View.GONE

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = MealCategoryAdapter { category ->
            val action = MealCategoryFragmentDirections.actionMealCategoryFragmentToMealListFragment(category.strCategory)
            findNavController().navigate(action)
        }
        binding.recyclerViewMealCategories.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@MealCategoryFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.mealCategories.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.lottieAnimationView.cancelAnimation()
                        binding.recyclerViewMealCategories.visibility = View.VISIBLE
                        adapter.submitList(resource.data ?: emptyList())
                    }
                }

                is Resource.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.lottieAnimationView.cancelAnimation()
                    binding.recyclerViewMealCategories.visibility = View.VISIBLE
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.recyclerViewMealCategories.visibility = View.GONE
                }
            }
        }
    }
}