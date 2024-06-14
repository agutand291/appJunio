package com.example.aplicacionjunio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private var itemList: List<Item>,
    private val characters: List<Character> = emptyList(),
    private val showCost: Boolean = true,
    private val onItemClick: (Item) -> Unit,
    private val onItemLongClick: (Item) -> Boolean
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    fun updateData(newItems: List<Item>) {
        itemList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_generic, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount() = itemList.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.item_name)
        private val itemCost: TextView = itemView.findViewById(R.id.item_cost)

        fun bind(item: Item) {
            itemName.text = item.name
            if (showCost) {
                itemCost.text = "${item.cost} oro"
                itemCost.visibility = View.VISIBLE
            } else itemCost.visibility = View.GONE

            itemView.setOnClickListener { onItemClick(item) }

            itemView.setOnLongClickListener { onItemLongClick(item) }
        }
    }
}
