package com.example.tastesip.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastesip.R
import com.example.tastesip.databinding.FragmentDetailBinding
import com.example.tastesip.ui.adapter.DetailAdapter
import com.example.tastesip.ui.viewmodel.RecipeViewModel
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.squareup.picasso.Picasso

abstract class DetailFragment<T : Any, I : Any> : Fragment() {

    protected lateinit var binding: FragmentDetailBinding
    protected abstract val viewModel: RecipeViewModel
    protected abstract val adapter: DetailAdapter<I>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimationView.playAnimation()
        binding.lottieAnimationView.visibility = View.VISIBLE
        binding.recyclerViewDetail.visibility = View.GONE

        setupRecyclerView()
        observeViewModel()
        fetchDetail()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DetailFragment.adapter
        }
    }

    protected open fun observeViewModel() {
        getDetailLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.lottieAnimationView.cancelAnimation()
                        binding.recyclerViewDetail.visibility = View.VISIBLE
                        resource.data?.let { detail ->
                            updateUI(detail)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.lottieAnimationView.cancelAnimation()
                    binding.recyclerViewDetail.visibility = View.VISIBLE
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.recyclerViewDetail.visibility = View.GONE
                }
            }
        }

        getDetailItemsLiveData().observe(viewLifecycleOwner) { items ->
            adapter.setItems(items)
        }
    }

    protected abstract fun fetchDetail()
    protected abstract fun getDetailLiveData(): LiveData<Resource<T?>>
    protected abstract fun getDetailItemsLiveData(): LiveData<List<I>>
    protected abstract fun updateUI(detail: T)

    protected fun loadImage(imageUrl: String) {
        Picasso.get().load(imageUrl).into(binding.detailImageView)
    }
}