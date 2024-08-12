package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.MealCategory
import com.example.tastesip.databinding.ItemMealCategoryBinding
import com.example.tastesip.util.CustomDiffUtil
import com.example.tastesip.util.calculateAndDispatch
import com.squareup.picasso.Picasso

class MealCategoryAdapter(
    private val onItemClick: (MealCategory) -> Unit
) : RecyclerView.Adapter<MealCategoryAdapter.MealCategoryViewHolder>() {

    private var mealCategories: List<MealCategory> = emptyList()
    private lateinit var binding: ItemMealCategoryBinding

    inner class MealCategoryViewHolder(private val binding: ItemMealCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mealCategory: MealCategory) {
            with(binding) {
                textViewMealCategoryName.text = mealCategory.strCategory
                Picasso.get().load(mealCategory.strCategoryThumb).into(imageViewMealCategory)
                root.setOnClickListener { onItemClick(mealCategory) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCategoryViewHolder {
        binding =
            ItemMealCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealCategoryViewHolder, position: Int) {
        holder.bind(mealCategories[position])
    }

    override fun getItemCount() = mealCategories.size

    fun submitList(newMealCategory: List<MealCategory>) {
        val diffUtil = CustomDiffUtil(mealCategories, newMealCategory)
        mealCategories = newMealCategory
        diffUtil.calculateAndDispatch(this)
    }
}