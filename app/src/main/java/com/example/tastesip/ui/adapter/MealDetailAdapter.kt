package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.databinding.ItemMealDetailBinding

class MealDetailAdapter : RecyclerView.Adapter<MealDetailAdapter.MealDetailViewHolder>() {

    private var items: List<MealDetailItem> = emptyList()
    private lateinit var binding: ItemMealDetailBinding

    fun setItems(newItems: List<MealDetailItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealDetailViewHolder {
        binding = ItemMealDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealDetailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class MealDetailViewHolder(private val binding: ItemMealDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealDetailItem) {
            when (item) {
                is MealDetailItem.Instruction -> binding.recipeInstructionsTextView.text = item.text
                is MealDetailItem.Ingredient -> binding.mealIngredientsTextView.text = item.text
            }
        }
    }
}

sealed class MealDetailItem {
    data class Instruction(val text: String) : MealDetailItem()
    data class Ingredient(val text: String) : MealDetailItem()
}