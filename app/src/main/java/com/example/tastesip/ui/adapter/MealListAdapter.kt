package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.Meal
import com.example.tastesip.databinding.ItemMealListBinding
import com.example.tastesip.util.CustomDiffUtil
import com.example.tastesip.util.calculateAndDispatch
import com.squareup.picasso.Picasso

class MealListAdapter(
    private val onItemClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    private var meals: List<Meal> = emptyList()
    private lateinit var binding: ItemMealListBinding

    inner class MealViewHolder(private val binding: ItemMealListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            with(binding) {
                textViewMealName.text = meal.strMeal
                Picasso.get().load(meal.strMealThumb).into(imageViewMeal)
                root.setOnClickListener { onItemClick(meal) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        binding =
            ItemMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount() = meals.size

    fun submitList(newMealList: List<Meal>) {
        val diffUtil = CustomDiffUtil(meals, newMealList)
        meals = newMealList
        diffUtil.calculateAndDispatch(this)
    }
}