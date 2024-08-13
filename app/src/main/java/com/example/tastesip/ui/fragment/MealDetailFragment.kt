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
import com.example.tastesip.data.model.MealDetailItem
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.databinding.FragmentMealDetailBinding
import com.example.tastesip.ui.adapter.MealDetailAdapter
import com.example.tastesip.ui.viewmodel.RecipeViewModel
import com.example.tastesip.ui.viewmodel.RecipeViewModelFactory
import com.example.tastesip.util.Resource
import com.squareup.picasso.Picasso
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        binding.recyclerViewMealDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MealDetailFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.mealDetail.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.lottieAnimationView.cancelAnimation()
                        binding.recyclerViewMealDetail.visibility = View.VISIBLE
                        resource.data?.let { mealDetail ->
                            binding.recipeTitleTextView.text = mealDetail.strMeal
                            Picasso.get().load(mealDetail.strMealThumb)
                                .into(binding.recipeImageView)
                        }
                    }
                }

                is Resource.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.lottieAnimationView.cancelAnimation()
                    binding.recyclerViewMealDetail.visibility = View.VISIBLE
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.recyclerViewMealDetail.visibility = View.GONE
                }
            }
        }
        viewModel.mealDetailItems.observe(viewLifecycleOwner) { items ->
            adapter.setItems(items)
        }
    }
}
