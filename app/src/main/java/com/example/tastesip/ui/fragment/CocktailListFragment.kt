package com.example.tastesip.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.R
import com.example.tastesip.data.api.RetrofitClient
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.databinding.FragmentCocktailListBinding
import com.example.tastesip.ui.adapter.CocktailListAdapter
import com.example.tastesip.ui.viewmodel.ListViewModel
import com.example.tastesip.ui.viewmodel.ListViewModelFactory
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CocktailListFragment : Fragment() {

    private lateinit var binding: FragmentCocktailListBinding
    private lateinit var adapter: CocktailListAdapter
    private val args: CocktailListFragmentArgs by navArgs()
    private val viewModel: ListViewModel by viewModels(factoryProducer = {
        ListViewModelFactory(
            MealRepository(RetrofitClient.mealApiService),
            CocktailRepository(RetrofitClient.cocktailApiService)
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCocktailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchCocktailsByCategory(category)
    }

    private fun setupRecyclerView() {
        adapter = CocktailListAdapter { cocktail ->
            val action =
                CocktailListFragmentDirections.actionCocktailListFragmentToCocktailDetailFragment(
                    cocktail.idDrink
                )
            findNavController().navigate(action)
        }
        with(binding) {
            recyclerViewCocktails.layoutManager = LinearLayoutManager(context)
            recyclerViewCocktails.adapter = adapter
        }
    }

    private fun observeViewModel() {
        viewModel.drinks.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.lottieAnimationView.cancelAnimation()
                        binding.recyclerViewCocktails.visibility = View.VISIBLE
                        resource.data?.let {
                            adapter.submitList(it)
                        }
                    }
                }

                is Resource.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.lottieAnimationView.cancelAnimation()
                    binding.recyclerViewCocktails.visibility = View.VISIBLE
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.recyclerViewCocktails.visibility = View.GONE
                }
            }
        }
    }
}