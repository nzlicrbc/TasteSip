package com.example.tastesip.ui.fragment

import CategoryViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.tastesip.R
import com.example.tastesip.databinding.FragmentCategoryBinding
import com.example.tastesip.ui.adapter.CategoryAdapter
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class CategoryFragment<T : Any> : Fragment() {

    protected lateinit var binding: FragmentCategoryBinding
    protected lateinit var adapter: CategoryAdapter<T>
    protected abstract val viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimationView.playAnimation()
        binding.lottieAnimationView.visibility = View.VISIBLE
        binding.recyclerViewCategories.visibility = View.GONE

        setupRecyclerView()
        observeViewModel()
    }

    abstract fun setupRecyclerView()

    protected open fun observeViewModel() {
        getLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.lottieAnimationView.cancelAnimation()
                        binding.recyclerViewCategories.visibility = View.VISIBLE
                        adapter.submitList(resource.data ?: emptyList())
                    }
                }
                is Resource.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.lottieAnimationView.cancelAnimation()
                    binding.recyclerViewCategories.visibility = View.VISIBLE
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.recyclerViewCategories.visibility = View.GONE
                }
            }
        }
    }

    abstract fun getLiveData(): LiveData<Resource<List<T>>>
}