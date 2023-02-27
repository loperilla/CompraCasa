package com.loperilla.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loperilla.compracasa.R
import com.loperilla.compracasa.databinding.ShoppingItemBinding
import com.loperilla.data.model.ShoppingListItem

class ShoppingListAdapter(
    private val shoppingList: List<ShoppingListItem>
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListVH {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.shopping_item, parent, false
        )

        return ShoppingListVH(view)
    }

    override fun getItemCount() = shoppingList.count()

    override fun onBindViewHolder(holder: ShoppingListVH, position: Int) {
        holder.bind(shoppingList[position])
    }

    inner class ShoppingListVH(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ShoppingItemBinding.bind(view)

        fun bind(shoppingItem: ShoppingListItem) {
            with(binding) {
                tvDate.text = shoppingItem.date
            }
        }
    }
}