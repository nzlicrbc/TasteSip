package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.CocktailDetailItem
import com.example.tastesip.databinding.ItemCocktailDetailBinding
import com.example.tastesip.util.CustomDiffUtil
import com.example.tastesip.util.calculateAndDispatch

class CocktailDetailAdapter :
    RecyclerView.Adapter<CocktailDetailAdapter.CocktailDetailViewHolder>() {

    private var items: List<CocktailDetailItem> = emptyList()
    private lateinit var binding: ItemCocktailDetailBinding

    fun setItems(newCocktailItems: List<CocktailDetailItem>) {
        val diffUtil = CustomDiffUtil(items, newCocktailItems)
        items = newCocktailItems
        diffUtil.calculateAndDispatch(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailDetailViewHolder {
        binding =
            ItemCocktailDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailDetailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class CocktailDetailViewHolder(private val binding: ItemCocktailDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CocktailDetailItem) {
            when (item) {
                is CocktailDetailItem.Instruction -> binding.cocktailInstructionsTextView.text =
                    item.text

                is CocktailDetailItem.Ingredient -> binding.cocktailIngredientsTextView.text =
                    item.text
            }
        }
    }
}