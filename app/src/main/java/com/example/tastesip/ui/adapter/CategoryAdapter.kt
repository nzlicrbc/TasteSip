package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.R
import com.example.tastesip.data.model.CocktailCategory
import com.example.tastesip.data.model.MealCategory
import com.example.tastesip.databinding.ItemCategoryBinding
import com.example.tastesip.util.CustomDiffUtil
import com.example.tastesip.util.calculateAndDispatch
import com.squareup.picasso.Picasso

class CategoryAdapter<T : Any>(
    private val onItemClick: (T) -> Unit
) : RecyclerView.Adapter<CategoryAdapter<T>.ViewHolder>() {

    private var categories: List<T> = emptyList()

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: T) {
            with(binding) {
                when (category) {
                    is MealCategory -> {
                        textViewCategoryName.text = category.strCategory
                        Picasso.get().load(category.strCategoryThumb).into(imageViewCategory)
                    }
                    is CocktailCategory -> {
                        textViewCategoryName.text = category.strCategory
                        imageViewCategory.setImageResource(R.drawable.cocktailcategory)
                    }
                }
                root.setOnClickListener { onItemClick(category) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    fun submitList(newCategories: List<T>) {
        val diffUtil = CustomDiffUtil(categories, newCategories)
        categories = newCategories
        diffUtil.calculateAndDispatch(this)
    }
}