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
import com.example.tastesip.databinding.FragmentCocktailListBinding
import com.example.tastesip.ui.adapter.CocktailListAdapter
import com.example.tastesip.ui.viewmodel.ListViewModel
import com.example.tastesip.ui.viewmodel.ListViewModelFactory
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar

class CocktailListFragment : Fragment() {

    private lateinit var binding: FragmentCocktailListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: CocktailListAdapter
    private val args: CocktailListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCocktailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealRepository = MealRepository(RetrofitClient.mealApiService)
        val cocktailRepository = CocktailRepository(RetrofitClient.cocktailApiService)
        val viewModelFactory = ListViewModelFactory(mealRepository, cocktailRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)

        val category = args.category
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchCocktailsByCategory(category)
    }

    private fun setupRecyclerView() {
        adapter = CocktailListAdapter(emptyList()) { cocktail ->
            val action = CocktailListFragmentDirections.actionCocktailListFragmentToCocktailDetailFragment(cocktail.idDrink)
            findNavController().navigate(action)
        }
        binding.recyclerViewCocktails.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCocktails.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.drinks.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        adapter.submitList(it)
                    }
                }
                is Resource.Error -> {
                    Snackbar.make(requireView(), resource.message ?: "An error occurred", Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                }
            }
        }
    }
}