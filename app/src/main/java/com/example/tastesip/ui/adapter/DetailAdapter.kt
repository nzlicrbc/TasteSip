package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.CocktailDetailItem
import com.example.tastesip.data.model.MealDetailItem
import com.example.tastesip.databinding.ItemDetailBinding
import com.example.tastesip.util.CustomDiffUtil
import com.example.tastesip.util.calculateAndDispatch

class DetailAdapter<T : Any> : RecyclerView.Adapter<DetailAdapter<T>.DetailViewHolder>() {

    private var items: List<T> = emptyList()

    fun setItems(newItems: List<T>) {
        val diffUtil = CustomDiffUtil(items, newItems)
        items = newItems
        diffUtil.calculateAndDispatch(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class DetailViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            val text = when (item) {
                is CocktailDetailItem.Instruction -> item.text
                is CocktailDetailItem.Ingredient -> item.text
                is MealDetailItem.Instruction -> item.text
                is MealDetailItem.Ingredient -> item.text
                else -> {""}
            }
            binding.detailItemTextView.text = text
        }
    }
}