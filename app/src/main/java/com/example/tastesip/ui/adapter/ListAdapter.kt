package com.example.tastesip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tastesip.data.model.Cocktail
import com.example.tastesip.data.model.Meal
import com.example.tastesip.databinding.ItemListBinding
import com.example.tastesip.util.CustomDiffUtil
import com.example.tastesip.util.calculateAndDispatch
import com.squareup.picasso.Picasso

class ListAdapter<T : Any>(
    private val onItemClick: (T) -> Unit
) : RecyclerView.Adapter<ListAdapter<T>.ViewHolder>() {

    private var items: List<T> = emptyList()

    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            with(binding) {
                when (item) {
                    is Meal -> {
                        textViewName.text = item.strMeal
                        Picasso.get().load(item.strMealThumb).into(imageViewItem)
                    }
                    is Cocktail -> {
                        textViewName.text = item.strDrink
                        Picasso.get().load(item.strDrinkThumb).into(imageViewItem)
                    }
                }
                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(newList: List<T>) {
        val diffUtil = CustomDiffUtil(items, newList)
        items = newList
        diffUtil.calculateAndDispatch(this)
    }
}