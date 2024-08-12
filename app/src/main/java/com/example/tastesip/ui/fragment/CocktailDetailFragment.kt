package com.example.tastesip.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.R
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.model.CocktailDetailItem
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.databinding.FragmentCocktailDetailBinding
import com.example.tastesip.ui.adapter.CocktailDetailAdapter
import com.example.tastesip.ui.viewmodel.RecipeViewModel
import com.example.tastesip.ui.viewmodel.RecipeViewModelFactory
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CocktailDetailFragment : Fragment() {

    private lateinit var binding: FragmentCocktailDetailBinding
    private lateinit var viewModel: RecipeViewModel
    private val args: CocktailDetailFragmentArgs by navArgs()
    private lateinit var adapter: CocktailDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCocktailDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealRepository = MealRepository(RetrofitClient.mealApiService)
        val cocktailRepository = CocktailRepository(RetrofitClient.cocktailApiService)
        val viewModelFactory = RecipeViewModelFactory(mealRepository, cocktailRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java)

        observeViewModel()
        viewModel.fetchCocktailDetail(args.cocktailId)

        adapter = CocktailDetailAdapter()
        binding.recyclerViewCocktailDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CocktailDetailFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.cocktailDetail.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { cocktailDetail ->
                        binding.cocktailNameTextView.text = cocktailDetail.strDrink
                        Picasso.get().load(cocktailDetail.strDrinkThumb)
                            .into(binding.cocktailImageView)

                        val items = mutableListOf<CocktailDetailItem>()
                        items.add(CocktailDetailItem.Instruction(cocktailDetail.strInstructions))

                        val ingredients = cocktailDetail.ingredientsAndMeasuresList
                            .filter { (ingredient, measure) -> !ingredient.isNullOrBlank() && !measure.isNullOrBlank() }
                            .joinToString("\n") { (ingredient, measure) -> "$measure $ingredient" }
                        items.add(CocktailDetailItem.Ingredient(ingredients))

                        adapter.setItems(items)
                    }
                }

                is Resource.Error -> {
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                }
            }
        }
    }
}
