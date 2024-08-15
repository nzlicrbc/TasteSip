package com.example.tastesip.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.tastesip.R
import com.example.tastesip.databinding.FragmentListBinding
import com.example.tastesip.ui.adapter.ListAdapter
import com.example.tastesip.ui.viewmodel.ListViewModel
import com.example.tastesip.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class ListFragment<T : Any> : Fragment() {

    protected lateinit var binding: FragmentListBinding
    protected lateinit var adapter: ListAdapter<T>
    protected abstract val viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        fetchData()
    }

    abstract fun setupRecyclerView()
    abstract fun fetchData()

    protected open fun observeViewModel() {
        getLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.lottieAnimationView.cancelAnimation()
                        binding.recyclerView.visibility = View.VISIBLE
                        resource.data?.let {
                            adapter.submitList(it)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.lottieAnimationView.cancelAnimation()
                    binding.recyclerView.visibility = View.VISIBLE
                    Snackbar.make(
                        requireView(),
                        resource.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }
    }

    abstract fun getLiveData(): LiveData<Resource<List<T>>>
}