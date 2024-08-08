package com.example.tastesip.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tastesip.R
import com.example.tastesip.databinding.FragmentNavigationBinding

class NavigationFragment : Fragment() {
    private lateinit var binding: FragmentNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonMeal.setOnClickListener {
                findNavController().navigate(R.id.action_mainActivity_to_mealCategoryFragment)
            }

            buttonCocktail.setOnClickListener {
                findNavController().navigate(R.id.action_mainActivity_to_cocktailCategoryFragment)
            }
        }
    }
}