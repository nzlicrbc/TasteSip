package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.Cocktail
import com.example.tastesip.databinding.ItemCocktailCategoryBinding
import com.squareup.picasso.Picasso

class CocktailListAdapter(
    private var cocktails: List<Cocktail>,
    private val onItemClick: (Cocktail) -> Unit
) : RecyclerView.Adapter<CocktailListAdapter.CocktailViewHolder>() {

    private lateinit var binding: ItemCocktailCategoryBinding //

    inner class CocktailViewHolder(private val binding: ItemCocktailCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: Cocktail) {
            with(binding) {
                textViewCocktailCategoryName.text = cocktail.strDrink
                Picasso.get().load(cocktail.strDrinkThumb).into(imageViewCocktailCategory)
                root.setOnClickListener { onItemClick(cocktail) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        binding =
            ItemCocktailCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(cocktails[position])
    }

    override fun getItemCount() = cocktails.size

    fun submitList(cocktailList: List<Cocktail>) {
        cocktails = cocktailList
        notifyDataSetChanged()
    }
}