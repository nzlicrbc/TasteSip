package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.R
import com.example.tastesip.data.model.CocktailCategory
import com.example.tastesip.databinding.ItemCocktailCategoryBinding

class CocktailCategoryAdapter(
    private var cocktailCategories: List<CocktailCategory>,
    private val onItemClick: (CocktailCategory) -> Unit
) : RecyclerView.Adapter<CocktailCategoryAdapter.CocktailCategoryViewHolder>() {

    private lateinit var binding: ItemCocktailCategoryBinding

    inner class CocktailCategoryViewHolder(binding: ItemCocktailCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktailCategory: CocktailCategory) {
            with(binding) {
                textViewCocktailCategoryName.text = cocktailCategory.strCategory
                imageViewCocktailCategory.setImageResource(R.drawable.cocktailcategory)
                root.setOnClickListener { onItemClick(cocktailCategory) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailCategoryViewHolder {
        binding =
            ItemCocktailCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailCategoryViewHolder, position: Int) {
        holder.bind(cocktailCategories[position])
    }

    override fun getItemCount() = cocktailCategories.size

    fun updateList(newCategories: List<CocktailCategory>) {
        cocktailCategories = newCategories
        notifyDataSetChanged()
    }
}