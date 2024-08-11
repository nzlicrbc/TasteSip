package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.Meal
import com.example.tastesip.databinding.ItemMealCategoryBinding
import com.squareup.picasso.Picasso

class MealListAdapter(
    private var meals: List<Meal>,
    private val onItemClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    private lateinit var binding: ItemMealCategoryBinding

    inner class MealViewHolder(private val binding: ItemMealCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            with(binding) {
                textViewMealCategoryName.text = meal.strMeal
                Picasso.get().load(meal.strMealThumb).into(imageViewMealCategory)
                root.setOnClickListener { onItemClick(meal) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        binding =
            ItemMealCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount() = meals.size

    fun submitList(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}