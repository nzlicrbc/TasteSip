package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.MealCategory
import com.example.tastesip.databinding.ItemMealCategoryBinding
import com.squareup.picasso.Picasso

class MealCategoryAdapter(
    private var mealCategories: List<MealCategory>,
    private val onItemClick: (MealCategory) -> Unit
) : RecyclerView.Adapter<MealCategoryAdapter.MealCategoryViewHolder>() {

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

        val displayMetrics = parent.context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        binding.root.layoutParams.width = screenWidth / 2
        return MealCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealCategoryViewHolder, position: Int) {
        holder.bind(mealCategories[position])
    }

    override fun getItemCount() = mealCategories.size

    fun submitList(mealCategoryList: List<MealCategory>) {
        mealCategories = mealCategoryList
        notifyDataSetChanged()
    }
}