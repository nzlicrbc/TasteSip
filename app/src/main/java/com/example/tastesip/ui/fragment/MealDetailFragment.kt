package com.example.tastesip.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.databinding.FragmentMealDetailBinding
import com.example.tastesip.ui.adapter.CocktailDetailAdapter
import com.example.tastesip.ui.adapter.CocktailDetailItem
import com.example.tastesip.ui.adapter.MealDetailAdapter
import com.example.tastesip.ui.adapter.MealDetailItem
import com.example.tastesip.ui.viewmodel.RecipeViewModel
import com.example.tastesip.ui.viewmodel.RecipeViewModelFactory
import com.example.tastesip.util.Resource
import com.squareup.picasso.Picasso
import com.google.android.material.snackbar.Snackbar

class MealDetailFragment : Fragment() {

    private lateinit var binding: FragmentMealDetailBinding
    private lateinit var viewModel: RecipeViewModel
    private val args: MealDetailFragmentArgs by navArgs()
    private lateinit var adapter: MealDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealRepository = MealRepository(RetrofitClient.mealApiService)
        val cocktailRepository = CocktailRepository(RetrofitClient.cocktailApiService)
        val viewModelFactory = RecipeViewModelFactory(mealRepository, cocktailRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java)

        observeViewModel()
        viewModel.fetchMealDetail(args.mealId)

        adapter = MealDetailAdapter()
        binding.recyclerViewMealDetail.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMealDetail.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.mealDetail.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { mealDetail ->
                        binding.recipeTitleTextView.text = mealDetail.strMeal
                        Picasso.get().load(mealDetail.strMealThumb).into(binding.recipeImageView)

                        val items = mutableListOf<MealDetailItem>()
                        items.add(MealDetailItem.Instruction(mealDetail.strInstructions))

                        val ingredients = mealDetail.ingredientsAndMeasuresList
                            .filter { (ingredient, measure) -> !ingredient.isNullOrBlank() && !measure.isNullOrBlank() }
                            .joinToString("\n") { (ingredient, measure) -> "$measure $ingredient" }
                        items.add(MealDetailItem.Ingredient(ingredients))

                        adapter.setItems(items)
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
